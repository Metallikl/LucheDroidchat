package com.dluche.luchedroidchat.ui.feature.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.ui.components.AppDialog
import com.dluche.luchedroidchat.ui.components.PrimaryButton
import com.dluche.luchedroidchat.ui.components.PrimaryTextField
import com.dluche.luchedroidchat.ui.theme.BackgroundGradient
import com.dluche.luchedroidchat.ui.theme.LucheDroidChatTheme

@Composable
fun SignInRoute(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val formState = viewModel.formState
    var errorMessages by remember { mutableStateOf<Int?>(null) }
    val context = LocalContext.current

    SignInScreen(
        formState = formState,
        onFormEvent = viewModel::onFormEvent,
        onRegisterClick = navigateToSignUp
    )

    LaunchedEffect(true) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is SignInEffect.SignInError -> {
                        when (effect.error) {
                            R.string.common_generic_error_message -> {
                                Toast.makeText(
                                    context,
                                    context.getString(effect.error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                errorMessages = effect.error
                            }
                        }
                    }

                    SignInEffect.SignInSuccess -> {
                        Toast.makeText(context, "Login realizado com sucesso!", Toast.LENGTH_LONG)
                            .show()
                        navigateToHome()
                    }
                }
            }
        }
    }

    errorMessages?.let { resId ->
        ErrorDialog(
            resId,
            onDismissRequest = {
                errorMessages = null
            },
            onConfirmButtonClick = {
                errorMessages = null
            }
        )
    }
}

@Composable
private fun ErrorDialog(
    error: Int,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    AppDialog(
        title = stringResource(id = R.string.common_generic_error_title),
        message = stringResource(error),
        onDismissRequest = onDismissRequest,
        onConfirmButtonClick = onConfirmButtonClick
    )
}

@Composable
fun SignInScreen(
    formState: SignInFormState,
    onFormEvent: (SignInFormEvent) -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BackgroundGradient)
            .padding(8.dp)
            .imePadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(78.dp))

        PrimaryTextField(
            value = formState.email,
            onValueChange = { email ->
                onFormEvent(SignInFormEvent.EmailChanged(email))
            },
            placeholder = stringResource(id = R.string.feature_login_email),
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.spacing_medium)),
            leadingIcon = R.drawable.ic_envelope,
            keyboardType = KeyboardType.Email,
            errorMessage = formState.emailError?.let {
                stringResource(id = it)
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        PrimaryTextField(
            value = formState.password,
            onValueChange = { pwd ->
                onFormEvent(SignInFormEvent.PasswordChanged(pwd))
            },
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.spacing_medium)),
            placeholder = stringResource(id = R.string.feature_login_password),
            keyboardType = KeyboardType.Password,
            leadingIcon = R.drawable.ic_lock,
            imeAction = ImeAction.Done,
            errorMessage = formState.passwordError?.let {
                stringResource(id = it)
            }
        )

        Spacer(modifier = Modifier.height(98.dp))

        PrimaryButton(
            text = stringResource(id = R.string.feature_login_button),
            onClick = {
                onFormEvent(SignInFormEvent.Submit)
            },
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.spacing_medium)),
            isLoading = formState.isLoading
        )

        Spacer(modifier = Modifier.height(56.dp))

        val noAccountText = stringResource(id = R.string.feature_login_no_account)
        val registerText = stringResource(id = R.string.feature_login_register)
        val noAccountRegisterText = "$noAccountText $registerText"
        val annotatedString = buildAnnotatedString {
            val registerTextStartIndex = noAccountRegisterText.indexOf(registerText)
            val registerTextEndIndex = registerTextStartIndex + registerText.length
            append(noAccountRegisterText)
            addStyle(
                style = SpanStyle(
                    color = Color.White
                ),
                start = 0,
                end = registerTextStartIndex
            )

            addLink(
                clickable = LinkAnnotation.Clickable(
                    tag = "register_text",
                    styles = TextLinkStyles(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    ),
                    linkInteractionListener = {
                        onRegisterClick()
                    }
                ),
                start = registerTextStartIndex,
                end = registerTextEndIndex
            )
        }

        Text(
            text = annotatedString,
        )
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    LucheDroidChatTheme {
        SignInScreen(
            formState = SignInFormState(),
            onFormEvent = {},
            onRegisterClick = {}
        )
    }
}

