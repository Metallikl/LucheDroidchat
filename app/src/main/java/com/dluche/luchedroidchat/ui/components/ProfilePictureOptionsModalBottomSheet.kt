package com.dluche.luchedroidchat.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.ui.theme.LucheDroidChatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePictureOptionsModalBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {

        ProfilePictureOptionRow(
            R.drawable.ic_photo_camera,
            R.string.common_take_photo,
            onClick = {}
        )

        ProfilePictureOptionRow(
            R.drawable.ic_photo_library,
            R.string.common_upload_photo,
            onClick = {}
        )
    }
}

@Composable
private fun ProfilePictureOptionRow(
    @DrawableRes
    iconResId: Int,
    @StringRes
    textStringId: Int,
    onClick: () -> Unit

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(
                id = iconResId
            ),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(id = textStringId),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ProfilePictureOptionsModalBottomSheetPreview() {
    val sheetState = SheetState(
        initialValue = SheetValue.Expanded,
        skipPartiallyExpanded = false,
        density = Density(LocalContext.current)

    )
    LucheDroidChatTheme {
        ProfilePictureOptionsModalBottomSheet(
            onDismissRequest = {},
            sheetState = sheetState
        )
    }

}