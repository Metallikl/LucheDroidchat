package com.dluche.luchedroidchat.data.mapper

import com.dluche.luchedroidchat.data.database.entity.MessageEntity
import com.dluche.luchedroidchat.model.ChatMessage

fun MessageEntity.asDomainModel(selfUserId: Int?): ChatMessage {
    return ChatMessage(
        autoId = autoId,
        id = id,
        senderId = senderId,
        receiverId = receiverId,
        isUnread = isUnread,
        text = text,
        formattedDateTime = timestamp.toTimestamp(),
        isSelf = senderId == selfUserId
    )
}