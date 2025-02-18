package com.dluche.luchedroidchat.data.mapper

import com.dluche.luchedroidchat.data.network.model.PaginatedChatResponse
import com.dluche.luchedroidchat.model.Chat
import com.dluche.luchedroidchat.model.User
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun PaginatedChatResponse.asDomainModel(selfUserId: Int?): List<Chat> {
    return chats.map { chatResponse ->
        Chat(
            chatResponse.id,
            chatResponse.lastMessage,
            chatResponse.members.map { userResponse ->
                User(
                    id = userResponse.id,
                    self = userResponse.id == selfUserId,
                    firstName = userResponse.firstName,
                    lastName = userResponse.lastName,
                    profilePictureUrl = userResponse.profilePictureUrl.orEmpty(),
                    username = userResponse.username
                )
            },
            unreadCount = chatResponse.unreadCount,
            timestamp = chatResponse.updatedAt.toTimestamp()
        )
    }
}

private fun Long.toTimestamp(): String {
    val messageDate = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )

    val now = LocalDateTime.now()
    return if(messageDate.toLocalDate() == now.toLocalDate()){
        messageDate.format(DateTimeFormatter.ofPattern("HH:mm"))
    } else{
        messageDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }
}