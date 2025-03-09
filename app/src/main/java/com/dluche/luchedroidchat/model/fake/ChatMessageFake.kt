package com.dluche.luchedroidchat.model.fake

import com.dluche.luchedroidchat.model.ChatMessage

val chatMessage1 = ChatMessage(
    autoId = 1,
    id = 1,
    senderId = 1,
    receiverId = 2,
    text = "Olá, tudo bem?",
    formattedDateTime = "10:00",
    isUnread = false,
    isSelf = true,
)

val chatMessage2 = ChatMessage(
    autoId = 2,
    id = 2,
    senderId = 2,
    receiverId = 1,
    text = "Tudo ótimo e você?",
    formattedDateTime = "10:01",
    isUnread = true,
    isSelf = false,
)

val chatMessage3 = ChatMessage(
    autoId = 3,
    id = 3,
    senderId = 1,
    receiverId = 2,
    text = "Também estou bem!",
    formattedDateTime = "10:02",
    isUnread = false,
    isSelf = true,
)

val chatMessage4 = ChatMessage(
    autoId = 4,
    id = 4,
    senderId = 2,
    receiverId = 1,
    text = "Que bom!",
    formattedDateTime = "10:03",
    isUnread = true,
    isSelf = false,
)

val chatMessage5 = ChatMessage(
    autoId = 5,
    id = 5,
    senderId = 1,
    receiverId = 2,
    text = "Pode me ajudar?",
    formattedDateTime = "10:05",
    isUnread = true,
    isSelf = true,
)

