package com.dluche.luchedroidchat.data.repository

import androidx.paging.PagingData
import com.dluche.luchedroidchat.model.Chat
import com.dluche.luchedroidchat.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun getChats(offset: Int, limit: Int): Result<List<Chat>>

    fun getPagedMessages(receiverId: Int): Flow<PagingData<ChatMessage>>

   suspend fun sendMessage(receiverId: Int, text: String)

}