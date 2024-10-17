package com.dluche.luchedroidchat.ui.feature.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.ui.validator.FormValidator

class SignUpViewModel(
    private val formValidator: FormValidator<SignUpFormState>
) : ViewModel() {

    var formState by mutableStateOf(SignUpFormState())
        private set

    fun onFormEvent(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.ProfilePictureChanged -> {
                formState = formState.copy(profilePictureUri = event.uri)
            }
            is SignUpFormEvent.FirstNameChanged -> {
                formState = formState.copy(firstName = event.firstName)
            }
            is SignUpFormEvent.LastNameChanged -> {
                formState = formState.copy(lastName = event.lastName)
            }
            is SignUpFormEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
            }
            is SignUpFormEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
                updatePasswordExtraText()
            }
            is SignUpFormEvent.PasswordConfirmationChanged -> {
                formState = formState.copy(passwordConfirmation = event.passwordConfirmation)
                updatePasswordExtraText()
            }
            SignUpFormEvent.OpenProfilePictureModalBottomSheet -> {
                formState = formState.copy(isProfilePictureModalBottomSheetOpen = true)
            }
            SignUpFormEvent.CloseProfilePictureModalBottomSheet -> {
                formState = formState.copy(isProfilePictureModalBottomSheetOpen = false)
            }
            SignUpFormEvent.Submit -> {
                doSignUp()
            }
        }
    }

    private fun updatePasswordExtraText() {
        formState = formState.copy(
            passwordExtraText = if (formState.password.isNotEmpty()
                && formState.password == formState.passwordConfirmation
            ) {
                R.string.feature_sign_up_passwords_match
            } else null
        )
    }

    private fun doSignUp() {
        if(isValidForm()){
            formState = formState.copy(isLoading = true)
        }
    }

    private fun isValidForm(): Boolean{
        return !formValidator.validate(formState).also { newState ->
            formState = newState
        }.hasError
    }
}