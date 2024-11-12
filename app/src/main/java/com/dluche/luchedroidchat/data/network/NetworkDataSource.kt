package com.dluche.luchedroidchat.data.network

import com.dluche.luchedroidchat.data.network.model.AuthRequest
import com.dluche.luchedroidchat.data.network.model.CreateAccountRequest
import com.dluche.luchedroidchat.data.network.model.TokenResponse

interface NetworkDataSource {
    suspend fun signUp(request: CreateAccountRequest)

    suspend fun signIn(request: AuthRequest): TokenResponse
}