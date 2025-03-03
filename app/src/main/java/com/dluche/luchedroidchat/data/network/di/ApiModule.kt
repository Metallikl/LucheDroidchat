package com.dluche.luchedroidchat.data.network.di

import com.dluche.luchedroidchat.data.manager.token.TokenManager
import com.dluche.luchedroidchat.model.NetworkException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.plugin
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    const val BASE_URL = "https://chat-api.androidmoderno.com.br/"
    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_BEARER = "Bearer"

    @Provides
    @Singleton
    fun provideHttpClient(
        tokenManager: TokenManager
    ): HttpClient {
        return HttpClient(CIO) {
            //melhora logs quando exception indicando a criação das exception por faixa de status code
            expectSuccess = true

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            defaultRequest {
                url(BASE_URL)
                contentType(ContentType.Application.Json)
            }
            //Adiciona tratativa de exception customizada a todas as resquest.
            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause, _ ->
                    throw if (cause is ClientRequestException) {
                        val errorMessage = cause.response.bodyAsText()
                        NetworkException.ApiException(errorMessage, cause.response.status.value)
                    } else {
                        NetworkException.UnknownNetworkException(cause)
                    }
                }
            }
        }.apply {
            //Configura interceptor //Se token existir, ktor adiciona no header da request.
            plugin(HttpSend).intercept { request ->
                val accessTokens = tokenManager.accessToken.firstOrNull()
                accessTokens?.let { token ->
                    request.headers.append(HEADER_AUTHORIZATION, "$HEADER_BEARER $token")
                }
                execute(request)
            }
        }
    }
}