package com.dluche.luchedroidchat.data.repository

import androidx.paging.PagingData
import com.dluche.luchedroidchat.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(limit: Int = 10): Flow<PagingData<User>>
}