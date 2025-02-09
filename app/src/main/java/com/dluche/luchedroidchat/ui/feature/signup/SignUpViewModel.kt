package com.dluche.luchedroidchat.ui.feature.signup

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.data.repository.AuthRepository
import com.dluche.luchedroidchat.model.CreateAccount
import com.dluche.luchedroidchat.model.NetworkException
import com.dluche.luchedroidchat.ui.validator.FormValidator
import com.dluche.luchedroidchat.util.error.ErrorConstants.HTTP_STATUS_USER_ALREADY_EXISTS
import com.dluche.luchedroidchat.util.error.ErrorConstants.HTTP_STATUS_VALIDATION_FAILURE
import com.dluche.luchedroidchat.util.image.ImageCompressor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val formValidator: FormValidator<SignUpFormState>,
    private val authRepository: AuthRepository,
    private val imageCompressor: ImageCompressor
) : ViewModel() {

    var formState by mutableStateOf(SignUpFormState())
        private set

    fun onFormEvent(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.ProfilePictureChanged -> {
                formState = formState.copy(profilePictureUri = event.uri)
                event.uri?.let {
                    compressImageAndUpdateState(it)
                }
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

            SignUpFormEvent.DismissErrorDialog -> dismissErrorDialog()
        }
    }

    private fun compressImageAndUpdateState(uri: Uri) {
        viewModelScope.launch {
            try {
                formState = formState.copy(isCompressingImage = true)
                val compressedFile = imageCompressor.compressAndResizeImage(uri)
                formState = formState.copy(profilePictureUri = compressedFile.toUri())
            } catch (e: Exception) {
                //todo
            } finally {
                formState = formState.copy(isCompressingImage = false)
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
        if (isValidForm()) {
            formState = formState.copy(isLoading = true)
            viewModelScope.launch {
                var profilePictureId: Int? = null
                var hasUploadFailed = false
                formState.profilePictureUri?.path?.let { path ->
                    authRepository.uploadProfilePicture(path).fold(
                        onSuccess = { imageData ->
                            profilePictureId = imageData.id
                        },
                        onFailure = {
                            formState = formState.copy(
                                isLoading = false,
                                profilePictureUri = null,
                                apiErrorMessageResId = R.string.error_message_profile_picture_uploading_failed
                            )
                            hasUploadFailed = true
                        }
                    )
                }

                if(hasUploadFailed) {
                   return@launch
                }

                authRepository.signUp(
                    CreateAccount(
                        username = formState.email,
                        password = formState.password,
                        firstName = formState.firstName,
                        lastName = formState.lastName,
                        profilePictureId = profilePictureId
                    )
                ).fold(
                    onSuccess = {
                        formState = formState.copy(
                            isLoading = false,
                            isSignedUp = true
                        )
                    },
                    onFailure = {
                        handleSignUpFailure(it)
                    }
                )
            }
        }
    }

    private fun handleSignUpFailure(failureCause: Throwable) {
        formState = formState.copy(
            isLoading = false,
            apiErrorMessageResId = if (failureCause is NetworkException.ApiException) {
                when (failureCause.statusCode) {
                    HTTP_STATUS_VALIDATION_FAILURE -> R.string.error_message_api_form_validation_failed
                    HTTP_STATUS_USER_ALREADY_EXISTS -> R.string.error_message_user_with_username_already_exists
                    else -> R.string.common_generic_error_message
                }
            } else {
                R.string.common_generic_error_message
            }
        )
    }

    private fun isValidForm(): Boolean {
        return !formValidator.validate(formState).also { newState ->
            formState = newState
        }.hasError
    }

    private fun dismissErrorDialog() {
        formState = formState.copy(apiErrorMessageResId = null)
    }
}