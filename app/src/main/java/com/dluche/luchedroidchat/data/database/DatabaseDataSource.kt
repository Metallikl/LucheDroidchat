package com.dluche.luchedroidchat.data.database

import androidx.paging.PagingSource
import com.dluche.luchedroidchat.data.database.entity.MessageEntity
import com.dluche.luchedroidchat.data.database.entity.MessageRemoteKeyEntity

interface DatabaseDataSource {
    fun getPagedMessages(receiverId: Int): PagingSource<Int, MessageEntity>

    suspend fun insertMessages(messages: List<MessageEntity>)

    suspend fun clearMessages(receiverId: Int)

    fun getMessageRemoteKey(receiverId: Int): MessageRemoteKeyEntity?

    suspend fun insertRemoteKey(messages: MessageRemoteKeyEntity)

    suspend fun clearRemoteKey(receiverId: Int)
}