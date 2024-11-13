package com.dluche.luchedroidchat.ui.feature.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.ui.components.PrimaryButton
import com.dluche.luchedroidchat.ui.components.ProfilePictureOptionsModalBottomSheet
import com.dluche.luchedroidchat.ui.components.ProfilePictureSelector
import com.dluche.luchedroidchat.ui.components.SecondaryTextField
import com.dluche.luchedroidchat.ui.theme.BackgroundGradient
import com.dluche.luchedroidchat.ui.theme.LucheDroidChatTheme
import kotlinx.coroutines.launch

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val formState = viewModel.formState
    SignUpScreen(
        formState = formState,
        onFormEvent = viewModel::onFormEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    formState: SignUpFormState,
    onFormEvent: (SignUpFormEvent) -> Unit
) {
    //fillMaxSize() com verticalScroll, não funciona corretamente, nesses casos, adcionar um box acima
    //adicionando as proprieades de verticalScroll, background etc no box e o fillMaxSize() no Column
    Box(
        modifier = Modifier
            .background(brush = BackgroundGradient)
            .verticalScroll(state = rememberScrollState())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(56.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                shape = MaterialTheme.shapes.extraLarge.copy(
                    bottomStart = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                ),
                color = MaterialTheme.colorScheme.surface,

                ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ProfilePictureSelector(
                        imageUri = formState.profilePictureUri,
                        modifier = Modifier.clickable {
                            onFormEvent(SignUpFormEvent.OpenProfilePictureModalBottomSheet)
                        }
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_first_name),
                        value = formState.firstName,
                        onValueChange = {
                            onFormEvent(SignUpFormEvent.FirstNameChanged(it))
                        },
                        errorText = formState.firstNameError?.let {
                            stringResource(
                                id = it,
                                stringResource(id = R.string.feature_sign_up_first_name)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_last_name),
                        value = formState.lastName,
                        onValueChange = {
                            onFormEvent(SignUpFormEvent.LastNameChanged(it))

                        },
                        errorText = formState.lastNameError?.let {
                            stringResource(
                                id = it,
                                stringResource(id = R.string.feature_sign_up_last_name)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_email),
                        value = formState.email,
                        onValueChange = {
                            onFormEvent(SignUpFormEvent.EmailChanged(it))

                        },
                        keyboardType = KeyboardType.Email,
                        errorText = formState.emailError?.let { stringResource(id = it) }
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_password),
                        value = formState.password,
                        onValueChange = {
                            onFormEvent(SignUpFormEvent.PasswordChanged(it))
                        },
                        keyboardType = KeyboardType.Password,
                        extraText = formState.passwordExtraText?.let { stringResource(id = it) },
                        errorText = formState.passwordError?.let { stringResource(id = it) }
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_password_confirmation),
                        value = formState.passwordConfirmation,
                        onValueChange = {
                            onFormEvent(SignUpFormEvent.PasswordConfirmationChanged(it))

                        },
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                        extraText = formState.passwordExtraText?.let { stringResource(id = it) },
                        errorText = formState.passwordConfirmationError?.let { stringResource(id = it) }
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    PrimaryButton(
                        text = stringResource(id = R.string.feature_sign_up_button),
                        onClick = {
                            onFormEvent(SignUpFormEvent.Submit)
                        }
                    )
                }
            }

            val sheetState = rememberModalBottomSheetState()
            val scope = rememberCoroutineScope() //criar scopo coroutine para fechar a sheet
            if (formState.isProfilePictureModalBottomSheetOpen) {
                ProfilePictureOptionsModalBottomSheet(
                    onPictureSelected = { uri ->
                        onFormEvent(SignUpFormEvent.ProfilePictureChanged(uri))
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion { //ao completar o job, atualiza o estado da modal
                            if (!sheetState.isVisible) {
                                onFormEvent(SignUpFormEvent.CloseProfilePictureModalBottomSheet)
                            }
                        }
                    },
                    onDismissRequest = {
                        onFormEvent(SignUpFormEvent.CloseProfilePictureModalBottomSheet)
                    },
                    sheetState = sheetState
                )
            }
        }
    }

}

@Preview
@Composable
private fun SignUpScreenPreview() {
    LucheDroidChatTheme {
        SignUpScreen(
            formState = SignUpFormState(),
            onFormEvent = {}
        )
    }
}
