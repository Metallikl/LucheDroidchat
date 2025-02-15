package com.dluche.luchedroidchat.ui.feature.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.data.repository.AuthRepository
import com.dluche.luchedroidchat.model.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    var formState by mutableStateOf(SignInFormState())
        private set

    private val _signInActionFlow = MutableSharedFlow<SignInAction>()
    val signInActionFlow = _signInActionFlow.asSharedFlow()

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
        if(formState.email.isBlank()){
            isFormValid = false
            formState = formState.copy(emailError = R.string.error_message_email_invalid)
        }
        if(formState.password.isBlank()){
            isFormValid = false
            formState = formState.copy(passwordError = R.string.error_message_password_invalid)
        }

        if (isFormValid) {
            formState = formState.copy(isLoading = true)
            viewModelScope.launch {
                authRepository.signIn(
                    email = formState.email,
                    password = formState.password,
                ).fold(
                    onSuccess = {
                        formState = formState.copy(isLoading = false)

                        _signInActionFlow.emit(SignInAction.Success)
                    },
                    onFailure = {
                        formState = formState.copy(isLoading = false)

                        val error = if (it is NetworkException.ApiException && it.statusCode == 401) {
                            SignInAction.Error.UnauthorizedError
                        } else {
                            SignInAction.Error.GenericError
                        }

                        _signInActionFlow.emit(error)
                    }
                )
            }
        }

    }

    sealed interface SignInAction {
        data object Success : SignInAction
        sealed interface Error : SignInAction {
            data object GenericError : Error
            data object UnauthorizedError : Error
        }
    }

}