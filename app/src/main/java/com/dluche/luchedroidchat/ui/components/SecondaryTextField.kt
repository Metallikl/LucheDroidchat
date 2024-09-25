package com.dluche.luchedroidchat.ui.components

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.ui.extention.bottomBorder
import com.dluche.luchedroidchat.ui.extention.getVisualTransformationForPassword
import com.dluche.luchedroidchat.ui.theme.ColorSuccess
import com.dluche.luchedroidchat.ui.theme.LucheDroidChatTheme
import io.ktor.client.plugins.convertLongTimeoutToIntWithInfiniteAsZero

@Composable
fun SecondaryTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    extraText: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
) {
    var inputText by remember { mutableStateOf(value) }

    var pwdVisible by remember { mutableStateOf(false) }

    BasicTextField(
        value = inputText,
        onValueChange = {
            inputText = it
            onValueChange(it)
        },
        modifier = modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            fontWeight = FontWeight.Bold
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = if (keyboardType == KeyboardType.Text) KeyboardCapitalization.Sentences else KeyboardCapitalization.None,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        singleLine = true,
        maxLines = 1,
        visualTransformation = keyboardType.getVisualTransformationForPassword(pwdVisible)
    ) { innerTextField ->
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                verticalAlignment = CenterVertically,
                modifier = Modifier
                    .bottomBorder(Color.Blue, 2.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        verticalAlignment = CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            innerTextField()
                        }
                        extraText?.let {
                            Text(
                                text = "extraText",
                                modifier = Modifier.padding(4.dp),
                                color = ColorSuccess,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }

                }

                if(keyboardType == KeyboardType.Password && inputText.isNotEmpty()){
                    val visibilityIcon = if (pwdVisible) {
                        R.drawable.ic_visibility
                    } else {
                        R.drawable.ic_visibility_off
                    }
                    IconButton(
                        onClick = {
                        pwdVisible = !pwdVisible
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = visibilityIcon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondaryTextFieldPreview() {
    LucheDroidChatTheme {
        SecondaryTextField(
            label = "Texto",
            value = "",
            onValueChange = {},
            extraText = "Extra text",
            keyboardType = KeyboardType.Email
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondaryTextFieldPasswordPreview() {
    LucheDroidChatTheme {
        SecondaryTextField(
            label = "Texto",
            value = "",
            onValueChange = {},
            extraText = "Passowrd matches text",
            keyboardType = KeyboardType.Password
        )
    }
}