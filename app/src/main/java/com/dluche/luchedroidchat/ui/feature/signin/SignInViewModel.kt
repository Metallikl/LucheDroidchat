package com.dluche.luchedroidchat.ui.feature.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.data.repository.AuthRepository
import com.dluche.luchedroidchat.model.NetworkException
import com.dluche.luchedroidchat.util.error.ErrorConstants.HTTP_STATUS_UNAUTHORIZED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var formState by mutableStateOf(SignInFormState())
        private set

    private val _effect = Channel<SignInEffect>()
    val effect = _effect.receiveAsFlow()

    fun onFormEvent(event: SignInFormEvent) {
        when (event) {
            is SignInFormEvent.EmailChanged -> {
                formState = formState.copy(
                    email = event.emailAddress,
                    emailError = null
                )
            }

            is SignInFormEvent.PasswordChanged -> {
                formState = formState.copy(
                    password = event.password,
                    passwordError = null
                )
            }

            SignInFormEvent.Submit -> {
                doSignIn()
            }
        }
    }

    private fun doSignIn() {
        var isFormValid = true
        //resetFormErrorsState()
        if (formState.email.isBlank()) {
            isFormValid = false
            formState = formState.copy(emailError = R.string.error_message_email_invalid)
        }
        if (formState.password.isBlank()) {
            isFormValid = false
            formState = formState.copy(passwordError = R.string.error_message_password_invalid)
        }

        if (isFormValid) {
            callSignInApi()
        }

    }

    private fun callSignInApi() {
        formState = formState.copy(
            isLoading = true
        )
        viewModelScope.launch {
            authRepository.signIn(
                formState.email,
                formState.password
            ).fold(
                onSuccess = {handleSignSuccess()},
                onFailure = {handleSignInError(it)}

            )
        }
    }

    private fun handleSignSuccess() {
        formState = formState.copy(isLoading = false)
        viewModelScope.launch {
            _effect.send(SignInEffect.SignInSuccess)
        }
    }

    private fun handleSignInError(error: Throwable) {
        val errorMessage = if (error is NetworkException.ApiException) {
            when (error.statusCode) {
                HTTP_STATUS_UNAUTHORIZED -> R.string.error_message_api_form_validation_failed
                else -> R.string.common_generic_error_message
            }
        } else {
            R.string.common_generic_error_message
        }
        formState = formState.copy(isLoading = false)
        _effect.trySend(SignInEffect.SignInError(errorMessage))
    }

}