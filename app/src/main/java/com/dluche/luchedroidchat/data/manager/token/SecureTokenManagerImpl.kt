package com.dluche.luchedroidchat.data.manager.token

import android.content.Context
import com.dluche.luchedroidchat.data.IoDispatcher
import com.dluche.luchedroidchat.data.datastore.TokensKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SecureTokenManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : TokenManager {

    override val accessToken: Flow<String>
        get() = flowOf(CryptoManager.decryptData(context, TokensKeys.ACCESS_TOKEN.name))

    override suspend fun saveAccessToken(token: String) {
        withContext(ioDispatcher) {
            CryptoManager.encryptData(context, TokensKeys.ACCESS_TOKEN.name, token)
        }
    }

    override suspend fun clearAccessToken() {
        withContext(ioDispatcher) {
            CryptoManager.encryptData(context, TokensKeys.ACCESS_TOKEN.name, "")
        }
    }
}