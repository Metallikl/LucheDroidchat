package com.dluche.luchedroidchat.data.local

interface LocalDataSource {
    suspend fun setToken(token: String)

    suspend fun getToken(): String?
}