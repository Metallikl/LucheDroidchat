package com.dluche.luchedroidchat.ui.feature.chats

import com.dluche.luchedroidchat.model.Chat

sealed interface ChatsListUiState {
    data class Success(val chats: List<Chat>) : ChatsListUiState
    data object Error : ChatsListUiState
    data object Loading : ChatsListUiState
}