package com.dluche.luchedroidchat.data.repository

import com.dluche.luchedroidchat.model.CreateAccount

interface AuthRepository {

    suspend fun signUp(createAccount: CreateAccount)

    suspend fun signIn(email: String, password: String)
}