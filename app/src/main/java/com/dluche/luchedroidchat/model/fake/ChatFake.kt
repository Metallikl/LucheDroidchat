package com.dluche.luchedroidchat.model.fake

import com.dluche.luchedroidchat.model.Chat
import com.dluche.luchedroidchat.model.User

val chat1 = Chat(
    id = 1,
    lastMessage = "Olá",
    members = listOf(
        user1,
        user2
    ),
    unreadCount = 0,
    timestamp = "12:35"
)

val chat2 = Chat(
    id = 1,
    lastMessage = "Como Vai?",
    members = listOf(
        user1,
        user3
    ),
    unreadCount = 0,
    timestamp = "12:35"
)

val chat3 = Chat(
    id = 1,
    lastMessage = "Feliz dia do trabalho?",
    members = listOf(
        user1,
        user4
    ),
    unreadCount = 2,
    timestamp = "12:35"
)