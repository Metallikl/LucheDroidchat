package com.dluche.luchedroidchat.data.network

import com.dluche.luchedroidchat.data.network.model.AuthRequest
import com.dluche.luchedroidchat.data.network.model.ChatResponse
import com.dluche.luchedroidchat.data.network.model.CreateAccountRequest
import com.dluche.luchedroidchat.data.network.model.ImageResponse
import com.dluche.luchedroidchat.data.network.model.PaginatedChatResponse
import com.dluche.luchedroidchat.data.network.model.PaginationParams
import com.dluche.luchedroidchat.data.network.model.TokenResponse
import com.dluche.luchedroidchat.data.network.model.UserResponse

interface NetworkDataSource {
    suspend fun signUp(request: CreateAccountRequest)

    suspend fun signIn(request: AuthRequest): TokenResponse

    suspend fun uploadProfilePicture(filePath: String): ImageResponse

    suspend fun authenticate(token: String): UserResponse

    suspend fun getChats(token: String,paginationParams: PaginationParams): PaginatedChatResponse
}