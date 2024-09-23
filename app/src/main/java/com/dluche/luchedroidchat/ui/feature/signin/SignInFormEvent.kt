package com.dluche.luchedroidchat.ui.feature.signin

sealed interface SignInFormEvent{
    data class EmailChanged(val emailAddress: String): SignInFormEvent
    data class PasswordChanged(val password: String): SignInFormEvent
    data object Submit: SignInFormEvent
}
