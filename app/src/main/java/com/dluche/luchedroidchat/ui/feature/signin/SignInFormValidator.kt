package com.dluche.luchedroidchat.ui.feature.signin

import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.ui.validator.EmailValidator
import com.dluche.luchedroidchat.ui.validator.FormValidator
import com.dluche.luchedroidchat.ui.validator.PasswordValidator

class SignInFormValidator : FormValidator<SignInFormState> {
    override fun validate(formState: SignInFormState): SignInFormState {
        val isEmailValid = formState.email.isNotEmpty()
        val isPasswordValid = formState.password.isNotEmpty()
        val hasError = listOf(
            isEmailValid,
            isPasswordValid,
        ).any { !it }

        return formState.copy(
            emailError = if (!isEmailValid) R.string.error_message_email_invalid else null,
            passwordError = if (!isPasswordValid) R.string.error_message_password_invalid else null,
            hasError = hasError,
        )
    }
}