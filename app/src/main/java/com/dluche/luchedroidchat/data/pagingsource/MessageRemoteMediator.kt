package com.dluche.luchedroidchat.data.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dluche.luchedroidchat.data.database.DatabaseDataSource
import com.dluche.luchedroidchat.data.database.LucheDroidChatDatabase
import com.dluche.luchedroidchat.data.database.entity.MessageEntity
import com.dluche.luchedroidchat.data.database.entity.MessageRemoteKeyEntity
import com.dluche.luchedroidchat.data.mapper.asEntityModel
import com.dluche.luchedroidchat.data.network.NetworkDataSource
import com.dluche.luchedroidchat.data.network.model.PaginationParams
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MessageRemoteMediator @Inject constructor(
    private val databaseDataSource: DatabaseDataSource,
    private val networkDataSource: NetworkDataSource,
    private val receiverId: Int,
    private val database: LucheDroidChatDatabase
) : RemoteMediator<Int, MessageEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MessageEntity>
    ): MediatorResult {
        return try {
            val offset = when (loadType) {
                REFRESH -> 0
                PREPEND -> return MediatorResult.Success(true)
                APPEND -> {
                    val remoteKey = databaseDataSource.getMessageRemoteKey(receiverId)
                    remoteKey?.nextOffset ?: return MediatorResult.Success(true)
                }
            }

            val limit = state.config.pageSize
            val paginationParam = PaginationParams(
                offset = offset.toString(),
                limit = limit.toString()
            )

            val response = networkDataSource.getMessages(receiverId, paginationParam)
            val entities = response.asEntityModel()

            database.withTransaction {
                if (loadType == REFRESH) {
                    databaseDataSource.clearMessages(receiverId)
                    databaseDataSource.clearRemoteKey(receiverId)
                }

                databaseDataSource.insertRemoteKey(
                    MessageRemoteKeyEntity(
                        receiverId = receiverId,
                        nextOffset = if (response.hasMore) {
                            offset + limit
                        } else null
                    )
                )

                databaseDataSource.insertMessages(
                    entities
                )
            }

            MediatorResult.Success(response.hasMore.not())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}