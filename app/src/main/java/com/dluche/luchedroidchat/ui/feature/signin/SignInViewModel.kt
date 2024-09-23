package com.dluche.luchedroidchat.ui.feature.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dluche.luchedroidchat.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {

    var formState by mutableStateOf(SignInFormState())
        private set

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

        if(isFormValid) {
            formState = formState.copy(
                isLoading = true
            )
        }

    }

    private fun resetFormErrorsState(){
        formState = formState.copy(
            emailError = null,
            passwordError = null

        )
    }

}