package com.dluche.luchedroidchat.data.repository

import com.dluche.luchedroidchat.data.IoDispatcher
import com.dluche.luchedroidchat.data.network.NetworkDataSource
import com.dluche.luchedroidchat.data.network.model.AuthRequest
import com.dluche.luchedroidchat.data.network.model.CreateAccountRequest
import com.dluche.luchedroidchat.model.CreateAccount
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AuthRepository {
    override suspend fun signUp(createAccount: CreateAccount): Result<Unit> {
        return withContext(dispatcher) {
            runCatching {
                networkDataSource.signUp(
                    request = CreateAccountRequest(
                        username = createAccount.username,
                        password = createAccount.password,
                        firstName = createAccount.firstName,
                        lastName = createAccount.lastName,
                        profilePictureId = createAccount.profilePictureId
                    )
                )
            }
        }
    }

    override suspend fun signIn(email: String, password: String) {
        networkDataSource.signIn(
            request = AuthRequest(
                username = email,
                password = password
            )
        )
    }
}