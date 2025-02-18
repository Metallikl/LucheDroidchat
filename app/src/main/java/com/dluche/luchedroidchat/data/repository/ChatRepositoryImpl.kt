package com.dluche.luchedroidchat.data.repository

import com.dluche.luchedroidchat.data.IoDispatcher
import com.dluche.luchedroidchat.data.manager.selfuser.SelfUserManager
import com.dluche.luchedroidchat.data.manager.token.TokenManager
import com.dluche.luchedroidchat.data.mapper.asDomainModel
import com.dluche.luchedroidchat.data.network.NetworkDataSource
import com.dluche.luchedroidchat.data.network.model.PaginationParams
import com.dluche.luchedroidchat.model.Chat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val tokenManager: TokenManager,
    private val selfUserManager: SelfUserManager,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ChatRepository {
    override suspend fun getChats(offset: Int, limit: Int): Result<List<Chat>> {
        return withContext(dispatcher) {
            runCatching {
                val token = tokenManager.accessToken.firstOrNull().orEmpty()
                val paginatedChatResponse = networkDataSource.getChats(
                    token = token,
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
}