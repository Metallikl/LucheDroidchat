package com.dluche.luchedroidchat.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val token: String,
)
