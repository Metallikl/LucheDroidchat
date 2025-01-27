package com.dluche.luchedroidchat.data.network

import com.dluche.luchedroidchat.data.network.model.AuthRequest
import com.dluche.luchedroidchat.data.network.model.CreateAccountRequest
import com.dluche.luchedroidchat.data.network.model.ImageResponse
import com.dluche.luchedroidchat.data.network.model.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
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
        }.body()
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

    companion object {
        const val SIGN_IN_PATH = "signin"
        const val SIGN_UP_PATH = "signup"
        const val PROFILE_PICTURE_PATH = "profile-picture"
        const val PROFILE_PICTURE_METADATA_KEY   = "filePicture"
        const val CONTENT_TYPE_IMAGE = "image/png"
    }
}