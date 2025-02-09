package com.dluche.luchedroidchat.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dluche.luchedroidchat.R

@Composable
fun AppDialog(
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    message: String,
    modifier: Modifier = Modifier,
    title: String? = null,
    confirmButtonText: String = stringResource(id = R.string.common_ok)
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmButtonClick
            ) {
                Text(
                    text = confirmButtonText
                )
            }
        },
        modifier = modifier,
        title = {
            title?.let {
                Text(
                    text = it
                )
            }
        },
        text = {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface
    )
}