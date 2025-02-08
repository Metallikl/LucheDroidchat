package com.dluche.luchedroidchat.data.repository

import com.dluche.luchedroidchat.model.CreateAccount
import com.dluche.luchedroidchat.model.ImageData
import com.dluche.luchedroidchat.model.SignInData

interface AuthRepository {

    suspend fun signUp(createAccount: CreateAccount): Result<Unit>

    suspend fun signIn(email: String, password: String) : Result<SignInData>

    suspend fun uploadProfilePicture(filePath: String): Result<ImageData>
}