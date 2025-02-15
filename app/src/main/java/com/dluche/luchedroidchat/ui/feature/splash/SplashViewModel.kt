package com.dluche.luchedroidchat.ui.feature.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dluche.luchedroidchat.data.repository.AuthRepository
import com.dluche.luchedroidchat.model.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _authenticationState = Channel<AuthenticationState>()
    val authenticationState = _authenticationState.receiveAsFlow()

    var showErrorDialogState by mutableStateOf(false)
        private set

    fun checkSession() {
        dismissErrorDialog()
        viewModelScope.launch {
            val accessToken = authRepository.getAccessToken()

            if (accessToken.isNullOrBlank()) {
                _authenticationState.send(AuthenticationState.UserNotAuthenticated)
                return@launch
            }

            authRepository.authenticate(accessToken).fold(
                onSuccess = {
                    _authenticationState.send(AuthenticationState.UserAuthenticated)
                },
                onFailure = {
                    if (it is NetworkException.ApiException && it.statusCode == 401) {
                        authRepository.clearAccessToken()
                        _authenticationState.send(AuthenticationState.UserNotAuthenticated)
                    } else {
                        showErrorDialogState = true
                    }
                }
            )
        }
    }

    fun dismissErrorDialog() {
        showErrorDialogState = false
    }

    sealed interface AuthenticationState {
        data object UserAuthenticated : AuthenticationState
        data object UserNotAuthenticated : AuthenticationState
    }
}