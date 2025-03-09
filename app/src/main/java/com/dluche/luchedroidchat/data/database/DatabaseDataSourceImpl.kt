package com.dluche.luchedroidchat.data.database

import androidx.paging.PagingSource
import com.dluche.luchedroidchat.data.database.entity.MessageEntity
import com.dluche.luchedroidchat.data.database.entity.MessageRemoteKeyEntity
import javax.inject.Inject

class DatabaseDataSourceImpl @Inject constructor(
    database: LucheDroidChatDatabase
) : DatabaseDataSource {

    private val messageDao = database.messageDao()
    private val remoteKeyDao = database.messageRemoteKeyDao()


    override fun getPagedMessages(receiverId: Int): PagingSource<Int, MessageEntity> {
        return messageDao.getPagedMessages(receiverId)
    }

    override suspend fun insertMessages(messages: List<MessageEntity>) {
        messageDao.insertMessages(messages)
    }

    override suspend fun clearMessages(receiverId: Int) {
        messageDao.clearMessages(receiverId)
    }

    override fun getMessageRemoteKey(receiverId: Int): MessageRemoteKeyEntity? {
        return remoteKeyDao.getRemoteKey(receiverId)
    }

    override suspend fun insertRemoteKey(messages: MessageRemoteKeyEntity) {
        remoteKeyDao.insertRemoteKey(messages)
    }

    override suspend fun clearRemoteKey(receiverId: Int) {
        remoteKeyDao.clearMessageRemoteKeys(receiverId)
    }
}