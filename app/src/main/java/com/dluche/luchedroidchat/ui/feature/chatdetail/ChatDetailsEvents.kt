package com.dluche.luchedroidchat.ui.feature.chatdetail

sealed interface ChatDetailsEvents {
    data class OnMessageChange(val message: String) : ChatDetailsEvents
    object OnSendMessage : ChatDetailsEvents
}