package com.dluche.luchedroidchat.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.dluche.luchedroidchat.data.IoDispatcher
import com.dluche.luchedroidchat.data.database.DatabaseDataSource
import com.dluche.luchedroidchat.data.database.LucheDroidChatDatabase
import com.dluche.luchedroidchat.data.database.entity.MessageEntity
import com.dluche.luchedroidchat.data.manager.selfuser.SelfUserManager
import com.dluche.luchedroidchat.data.mapper.asDomainModel
import com.dluche.luchedroidchat.data.network.NetworkDataSource
import com.dluche.luchedroidchat.data.network.model.PaginationParams
import com.dluche.luchedroidchat.data.pagingsource.MessageRemoteMediator
import com.dluche.luchedroidchat.model.Chat
import com.dluche.luchedroidchat.model.ChatMessage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val selfUserManager: SelfUserManager,
    private val databaseDataSource: DatabaseDataSource,
    private val database: LucheDroidChatDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ChatRepository {
    override suspend fun getChats(offset: Int, limit: Int): Result<List<Chat>> {
        return withContext(dispatcher) {
            runCatching {
                val paginatedChatResponse = networkDataSource.getChats(
                    paginationParams = PaginationParams(
                        offset = offset.toString(),
                        limit = limit.toString()
                    )
                )
                val selfUser = selfUserManager.selfUserFlow.firstOrNull()
                paginatedChatResponse.asDomainModel(selfUser?.id)
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedMessages(receiverId: Int): Flow<PagingData<ChatMessage>> {
        val selfUserId = runBlocking { selfUserManager.selfUserFlow.firstOrNull() }
        return Pager(
            PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = MessageRemoteMediator(
                networkDataSource = networkDataSource,
                receiverId = receiverId,
                databaseDataSource = databaseDataSource,
                database = database
            ),
            pagingSourceFactory = {
                databaseDataSource.getPagedMessages(receiverId)
            }
        ).flow.map {
            it.map { messageEntity ->
                messageEntity.asDomainModel(selfUserId?.id)
            }
        }
    }

    override suspend fun sendMessage(
        receiverId: Int,
        text: String
    ) {
        val selfUserId = selfUserManager.selfUserFlow.firstOrNull()
        val messageEntity = MessageEntity(
            id = null,
            isUnread = false,
            senderId = selfUserId?.id ?: 0,
            receiverId = receiverId,
            text = text,
            timestamp = Instant.now().toEpochMilli()
        )
        databaseDataSource.insertMessages(
            listOf(messageEntity)
        )
    }
}