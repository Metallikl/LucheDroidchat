package com.dluche.luchedroidchat.data.repository

import com.dluche.luchedroidchat.model.Chat

interface ChatRepository {

    suspend fun getChats(offset: Int, limit: Int): Result<List<Chat>>
}