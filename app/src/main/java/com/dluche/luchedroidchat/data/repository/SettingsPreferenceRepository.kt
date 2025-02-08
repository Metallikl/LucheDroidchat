package com.dluche.luchedroidchat.data.repository

interface SettingsPreferenceRepository {
    suspend fun setToken(token: String): Result<Unit>

    suspend fun getToken(): Result<String?>
}