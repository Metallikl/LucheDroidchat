package com.dluche.luchedroidchat.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadStates
import androidx.paging.LoadState

class PagingUsersPreviewParameterProvider : PreviewParameterProvider<LoadStates> {
    override val values: Sequence<LoadStates> = sequenceOf(
        LoadStates(
            refresh = LoadState.Loading,
            prepend = LoadState.NotLoading(false),
            append = LoadState.NotLoading(false)
        ),
        LoadStates(
            refresh = LoadState.Error(Throwable()),
            prepend = LoadState.NotLoading(false),
            append = LoadState.NotLoading(false)
        ),
        LoadStates(
            refresh = LoadState.NotLoading(true),
            prepend = LoadState.NotLoading(false),
            append = LoadState.NotLoading(false)
        )
    )
}