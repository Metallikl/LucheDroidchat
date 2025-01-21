package com.dluche.luchedroidchat.data.repository

import com.dluche.luchedroidchat.model.CreateAccount

interface AuthRepository {

    suspend fun signUp(createAccount: CreateAccount): Result<Unit>

    suspend fun signIn(email: String, password: String)
}