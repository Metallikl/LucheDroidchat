package com.dluche.luchedroidchat.data.mapper

import com.dluche.luchedroidchat.data.database.entity.MessageEntity
import com.dluche.luchedroidchat.data.network.model.PaginatedMessageResponse

fun PaginatedMessageResponse.asEntityModel(): List<MessageEntity> {
    return messages.map { messageResponse ->
        MessageEntity(
            id = messageResponse.id,
            isUnread = messageResponse.isUnread,
            receiverId = messageResponse.receiverId,
            senderId = messageResponse.senderId,
            text = messageResponse.text,
            timestamp = messageResponse.timestamp
        )
    }
}

