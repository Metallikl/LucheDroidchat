package com.dluche.luchedroidchat.model

data class User(
    val id: Int,
    val self: Boolean,
    val firstName: String,
    val lastName: String,
    val profilePictureUrl: String?,
    val username: String
)
