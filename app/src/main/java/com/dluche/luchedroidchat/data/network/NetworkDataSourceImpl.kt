package com.dluche.luchedroidchat.data.network

import com.dluche.luchedroidchat.data.network.model.AuthRequest
import com.dluche.luchedroidchat.data.network.model.CreateAccountRequest
import com.dluche.luchedroidchat.data.network.model.ImageResponse
import com.dluche.luchedroidchat.data.network.model.PaginatedChatResponse
import com.dluche.luchedroidchat.data.network.model.PaginatedMessageResponse
import com.dluche.luchedroidchat.data.network.model.PaginatedUserResponse
import com.dluche.luchedroidchat.data.network.model.PaginationParams
import com.dluche.luchedroidchat.data.network.model.TokenResponse
import com.dluche.luchedroidchat.data.network.model.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.URLBuilder
import java.io.File
import javax.inject.Inject


class NetworkDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : NetworkDataSource {
    override suspend fun signUp(request: CreateAccountRequest) {
        client.post(SIGN_UP_PATH) {
            setBody(request)
        }.body<Unit>()

    }

    override suspend fun signIn(request: AuthRequest): TokenResponse {
        return client.post(SIGN_IN_PATH) {
            setBody(request)
        }.body<TokenResponse>()
    }

    override suspend fun uploadProfilePicture(filePath: String): ImageResponse {
        val file = File(filePath)

        return client.submitFormWithBinaryData(
            url = PROFILE_PICTURE_PATH,
            formData = formData {
                append(PROFILE_PICTURE_METADATA_KEY, file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentType, CONTENT_TYPE_IMAGE)
                    append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                })
            }
        ).body()
    }

    override suspend fun authenticate(): UserResponse {
        return client.get(AUTH_PATH).body()
    }

    override suspend fun getChats(
        paginationParams: PaginationParams
    ): PaginatedChatResponse {
        return client.get(CHATS_PATH) {
            url {
                appendPaginationParams(paginationParams)
            }
        }.body()
    }

    override suspend fun getUser(paginationParams: PaginationParams): PaginatedUserResponse {
        return client.get(USERS_PATH) {
            url {
                appendPaginationParams(paginationParams)
            }
        }.body()
    }

    override suspend fun getMessages(
        receiverId: Int,
        paginationParams: PaginationParams
    ): PaginatedMessageResponse {
        return client.get("$MESSAGES_PATH/$receiverId") {
            url {
                appendPaginationParams(paginationParams)
            }
        }.body()
    }

    private fun URLBuilder.appendPaginationParams(paginationParams: PaginationParams) {
        parameters.append(OFFSET_PARAM, paginationParams.offset)
        parameters.append(LIMIT_PARAM, paginationParams.limit)
    }

    companion object {
        const val SIGN_IN_PATH = "signin"
        const val SIGN_UP_PATH = "signup"
        const val AUTH_PATH = "authenticate"
        const val PROFILE_PICTURE_PATH = "profile-picture"
        const val PROFILE_PICTURE_METADATA_KEY = "filePicture"
        const val CONTENT_TYPE_IMAGE = "image/png"
        const val OFFSET_PARAM = "offset"
        const val LIMIT_PARAM = "limit"
        const val CHATS_PATH = "conversations"
        const val USERS_PATH = "users"
        const val MESSAGES_PATH = "messages"
    }
}