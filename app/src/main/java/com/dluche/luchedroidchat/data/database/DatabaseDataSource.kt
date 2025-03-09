package com.dluche.luchedroidchat.data.database

import androidx.paging.PagingSource
import com.dluche.luchedroidchat.data.database.entity.MessageEntity

interface DatabaseDataSource {
    suspend fun getPagedMessages(receiverId: Int): PagingSource<Int, MessageEntity>
    suspend fun insertMessages(messages: List<MessageEntity>)
    suspend fun clearMessages(receiverId: Int)
}