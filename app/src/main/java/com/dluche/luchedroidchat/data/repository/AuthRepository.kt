package com.dluche.luchedroidchat.data.repository

import com.dluche.luchedroidchat.model.CreateAccount
import com.dluche.luchedroidchat.model.ImageData

interface AuthRepository {

    suspend fun getAccessToken(): String?

    suspend fun clearAccessToken()

    suspend fun signUp(createAccount: CreateAccount): Result<Unit>

    suspend fun signIn(email: String, password: String): Result<Unit>

    suspend fun uploadProfilePicture(filePath: String): Result<ImageData>

    suspend fun authenticate(): Result<Unit>
}