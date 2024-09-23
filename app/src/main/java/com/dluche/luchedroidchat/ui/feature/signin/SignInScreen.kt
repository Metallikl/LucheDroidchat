package com.dluche.luchedroidchat.ui.feature.signin

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.ui.components.PrimaryButton
import com.dluche.luchedroidchat.ui.components.PrimaryTextField
import com.dluche.luchedroidchat.ui.theme.BackgroundGradient
import com.dluche.luchedroidchat.ui.theme.LucheDroidChatTheme

@Composable
fun SignInRoute() {
    SignInScreen()
}

@Composable
fun SignInScreen() {
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

        var email by remember {
            mutableStateOf("")
        }
        var passowrd by remember {
            mutableStateOf("")
        }

        PrimaryTextField(
            value = email,
            onValueChange = {
                email = it
            },
            placeholder = stringResource(id = R.string.feature_login_email),
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.spacing_medium)),
            leadingIcon = R.drawable.ic_envelope,
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(14.dp))

        PrimaryTextField(
            value = passowrd,
            onValueChange = {
                passowrd = it
            },
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.spacing_medium)),
            placeholder = stringResource(id = R.string.feature_login_password),
            keyboardType = KeyboardType.Password,
            leadingIcon = R.drawable.ic_lock,
            imeAction = ImeAction.Done
        )

        Spacer(modifier = Modifier.height(98.dp))

        var isLoading by remember {
            mutableStateOf(false)
        }

        PrimaryButton(
            text = stringResource(id = R.string.feature_login_button),
            onClick = { isLoading = !isLoading },
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.spacing_medium)),
            isLoading = isLoading
        )
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    LucheDroidChatTheme {
        SignInScreen()
    }

}

