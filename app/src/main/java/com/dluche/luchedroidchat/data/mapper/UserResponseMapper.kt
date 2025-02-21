package com.dluche.luchedroidchat.data.mapper

import com.dluche.luchedroidchat.data.network.model.PaginatedUserResponse
import com.dluche.luchedroidchat.model.User

fun PaginatedUserResponse.asDomainModel(): List<User> {
    return users.map { userResponse ->
        User(
            id = userResponse.id,
            self = false,
            firstName = userResponse.firstName,
            lastName = userResponse.lastName,
            profilePictureUrl = userResponse.profilePictureUrl,
            username = userResponse.username,
        )
    }
}

