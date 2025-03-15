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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val chatDetailRoute = savedStateHandle.toRoute<Route.ChatDetailRoute>()
    val sendMessageFlow = MutableSharedFlow<Unit>()

    var messageText by mutableStateOf("")
        private set

    val pagingChatMessage = chatRepository.getPagedMessages(
        chatDetailRoute.userId
    ).cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            //Utilizando mapLatest e flaMapLastest do flow, automaticamente é cancelado
            // a coroutine atual, funcionando como o job cancel()
            sendMessageFlow.mapLatest {
                sendMessage()
            }.collect()

        }
    }

    fun dispatchEvent(event: ChatDetailsEvents) {
        when (event) {
            is ChatDetailsEvents.OnMessageChange -> updateMessage(event.message)
            ChatDetailsEvents.OnSendMessage -> sendMessageFlow()
        }
    }

    private fun updateMessage(message: String) {
        messageText = message
    }

    private fun sendMessage() {
        viewModelScope.launch {
            chatRepository.sendMessage(
                receiverId = chatDetailRoute.userId,
                text = messageText
            )
        }
    }

    private fun sendMessageFlow() {
        viewModelScope.launch {
            sendMessageFlow.emit(Unit)
        }
    }
}