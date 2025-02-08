package com.dluche.luchedroidchat.data.repository

import com.dluche.luchedroidchat.data.IoDispatcher
import com.dluche.luchedroidchat.data.local.LocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsPreferenceRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SettingsPreferenceRepository {
    override suspend fun setToken(token: String): Result<Unit> {
        return withContext(dispatcher) {
            runCatching {
                localDataSource.setToken(token)
            }
        }
    }

    override suspend fun getToken(): Result<String?> {
        return withContext(dispatcher) {
            runCatching {
                localDataSource.getToken()
            }
        }
    }
}