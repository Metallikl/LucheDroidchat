package com.dluche.luchedroidchat.model

data class CreateAccount(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val profilePictureId: String?
)