package com.dluche.luchedroidchat.ui.feature.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.ui.components.PrimaryButton
import com.dluche.luchedroidchat.ui.components.SecondaryTextField
import com.dluche.luchedroidchat.ui.theme.BackgroundGradient
import com.dluche.luchedroidchat.ui.theme.LucheDroidChatTheme

@Composable
fun SignUpRoute() {
    SignUpRouteScreen()
}

@Composable
fun SignUpRouteScreen() {
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
                ) {

                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_first_name),
                        value = "",
                        onValueChange = {}
                    )

                    Spacer(modifier = Modifier.height(22.dp))


                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_last_name),
                        value = "",
                        onValueChange = {}
                    )

                    Spacer(modifier = Modifier.height(22.dp))


                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_email),
                        value = "",
                        onValueChange = {},
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(22.dp))


                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_password),
                        value = "",
                        onValueChange = {},
                        keyboardType = KeyboardType.Password
                    )

                    Spacer(modifier = Modifier.height(22.dp))


                    SecondaryTextField(
                        label = stringResource(id = R.string.feature_sign_up_password_confirmation),
                        value = "",
                        onValueChange = {},
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    PrimaryButton(
                        text = stringResource(id = R.string.feature_sign_up_button),
                        onClick = {}
                    )


                }
            }
        }
    }

}

@Preview
@Composable
private fun SignUpRouteScreenPreview() {
    LucheDroidChatTheme {
        SignUpRouteScreen()
    }
}
