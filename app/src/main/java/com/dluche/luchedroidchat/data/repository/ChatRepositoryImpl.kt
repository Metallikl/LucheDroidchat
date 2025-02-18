package com.dluche.luchedroidchat.data.repository

import com.dluche.luchedroidchat.data.IoDispatcher
import com.dluche.luchedroidchat.data.manager.selfuser.SelfUserManager
import com.dluche.luchedroidchat.data.manager.token.TokenManager
import com.dluche.luchedroidchat.data.network.NetworkDataSource
import com.dluche.luchedroidchat.data.network.model.PaginationParams
import com.dluche.luchedroidchat.model.Chat
import com.dluche.luchedroidchat.model.User
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
                val selfUser = selfUserManager.selfUserFlow.firstOrNull()
                val paginatedChatResponse = networkDataSource.getChats(
                    token = token,
                    paginationParams = PaginationParams(
                        offset = offset.toString(),
                        limit = limit.toString()
                    )
                )

                paginatedChatResponse.chats.map { chatResponse ->
                    Chat(
                        chatResponse.id,
                        chatResponse.lastMessage,
                        chatResponse.members.map { userResponse ->
                            User(
                                id = userResponse.id,
                                self = userResponse.id == selfUser?.id,
                                firstName = userResponse.firstName,
                                lastName = userResponse.lastName,
                                profilePictureUrl = userResponse.profilePictureUrl.orEmpty(),
                                username = userResponse.username
                            )
                        },
                        unreadCount = chatResponse.unreadCount,
                        timestamp = ""
                    )
                }
            }
        }
    }
}