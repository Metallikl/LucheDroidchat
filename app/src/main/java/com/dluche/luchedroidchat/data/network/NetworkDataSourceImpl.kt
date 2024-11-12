package com.dluche.luchedroidchat.data.network

import com.dluche.luchedroidchat.data.network.model.AuthRequest
import com.dluche.luchedroidchat.data.network.model.CreateAccountRequest
import com.dluche.luchedroidchat.data.network.model.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor (
    private val client: HttpClient
): NetworkDataSource {
    override suspend fun signUp(request: CreateAccountRequest) {
        client.post(SIGN_UP_PATH) {
            setBody(request)
        }.body<Unit>()
    }

    override suspend fun signIn(request: AuthRequest): TokenResponse {
        return client.post(SIGN_IN_PATH) {
            setBody(request)
        }.body()
    }

    companion object{
        const val SIGN_IN_PATH = "signin"
        const val SIGN_UP_PATH = "signup"

    }
}