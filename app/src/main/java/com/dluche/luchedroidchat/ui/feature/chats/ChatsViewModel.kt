package com.dluche.luchedroidchat.ui.feature.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dluche.luchedroidchat.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _chatsListUiState = MutableStateFlow<ChatsListUiState>(ChatsListUiState.Loading)
    val chatsListUiState = _chatsListUiState.asStateFlow()

    init {
        getChats()
    }

    private fun getChats() {
        viewModelScope.launch {
            chatRepository.getChats(
                offset = 0,
                limit = 10
            ).fold(
                onSuccess = { chats ->
                    _chatsListUiState.update {
                        ChatsListUiState.Success(chats)
                    }
                },
                onFailure = {
                    _chatsListUiState.update {
                        ChatsListUiState.Error
                    }
                }
            )
        }
    }
}