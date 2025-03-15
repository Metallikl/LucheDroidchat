package com.dluche.luchedroidchat.ui.feature.chatdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.dluche.luchedroidchat.data.repository.ChatRepository
import com.dluche.luchedroidchat.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val chatDetailRoute = savedStateHandle.toRoute<Route.ChatDetailRoute>()
    private var sendMessageJob: Job? = null

    var messageText by mutableStateOf("")
        private set

    val pagingChatMessage = chatRepository.getPagedMessages(
        chatDetailRoute.userId
    ).cachedIn(viewModelScope)

    fun dispatchEvent(event: ChatDetailsEvents) {
        when (event) {
            is ChatDetailsEvents.OnMessageChange -> updateMessage(event.message)
            ChatDetailsEvents.OnSendMessage -> sendMessage()
        }
    }

    private fun updateMessage(message: String){
        messageText = message
    }

    private fun sendMessage() {
        sendMessageJob?.cancel()
        sendMessageJob =  viewModelScope.launch {
            chatRepository.sendMessage(
                receiverId = chatDetailRoute.userId,
                text = messageText
            )
        }
    }
}