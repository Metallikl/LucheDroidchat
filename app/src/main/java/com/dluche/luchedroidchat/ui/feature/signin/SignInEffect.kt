package com.dluche.luchedroidchat.ui.feature.signin

sealed interface SignInEffect {
    data object SignInSuccess : SignInEffect
    data class SignInError(val error: Int) : SignInEffect
}
