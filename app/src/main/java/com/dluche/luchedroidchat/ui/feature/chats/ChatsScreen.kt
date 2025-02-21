@file:OptIn(ExperimentalMaterial3Api::class)

package com.dluche.luchedroidchat.ui.feature.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.model.Chat
import com.dluche.luchedroidchat.ui.components.AnimatedContent
import com.dluche.luchedroidchat.ui.components.ChatItem
import com.dluche.luchedroidchat.ui.components.ChatItemShimmer
import com.dluche.luchedroidchat.ui.components.GeneralEmptyList
import com.dluche.luchedroidchat.ui.components.GeneralError
import com.dluche.luchedroidchat.ui.components.PrimaryButton
import com.dluche.luchedroidchat.ui.preview.ChatListPreviewParameterProvider
import com.dluche.luchedroidchat.ui.theme.Grey1
import com.dluche.luchedroidchat.ui.theme.LucheDroidChatTheme

@Composable
fun ChatsRoute(
    viewModel: ChatsViewModel = hiltViewModel()
) {

    val iuState by viewModel.chatsListUiState.collectAsStateWithLifecycle()
    val onTryAgainClick = viewModel::getChats
    ChatsScreen(iuState, onTryAgainClick)
}

@Composable
fun ChatsScreen(
    iuState: ChatsListUiState,
    onTryAgainClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = AnnotatedString.fromHtml(
                            stringResource(
                                R.string.feature_chats_greeting,
                                "Daniel"
                            )
                        ),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                expandedHeight = 100.dp
            )
        },
        containerColor = MaterialTheme.colorScheme.primary,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.extraLarge.copy(
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp)
                    )
                )
                .clip(
                    shape = MaterialTheme.shapes.extraLarge.copy(
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp)
                    )
                )
                .fillMaxSize()
        ) {
            when (iuState) {
                ChatsListUiState.Loading -> {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        repeat(5) { index ->
                            ChatItemShimmer()
                            if (index < 4) {
                                HorizontalDivider(
                                    color = Grey1
                                )
                            }
                        }
                    }
                }

                is ChatsListUiState.Success -> {
                    when (iuState.chats.isNotEmpty()) {
                        true -> {
                            ChatsListContent(iuState.chats)

                        }

                        else -> {
                            GeneralEmptyList(
                                message = stringResource(R.string.feature_chats_empty_list),
                                resourceContent = {
                                    AnimatedContent(
                                        resId = R.raw.animation_empty_list
                                    )
                                }
                            )
                        }
                    }
                }

                ChatsListUiState.Error -> {
                    GeneralError(
                        title = stringResource(R.string.common_generic_error_title),
                        message = stringResource(R.string.common_generic_error_message),
                        resourceContent = {
                            AnimatedContent()
                        },
                        actionContent = {
                            PrimaryButton(
                                text = stringResource(R.string.common_try_again),
                                onClick = onTryAgainClick
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatsListContent(chats: List<Chat>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(chats) { index, chat ->
            ChatItem(chat)
            if (index < chats.lastIndex) {
                HorizontalDivider(
                    color = Grey1
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChatsScreenLoadingPreview() {
    LucheDroidChatTheme {
        ChatsScreen(
            iuState = ChatsListUiState.Loading,
            onTryAgainClick = {}
        )
    }
}

@Preview
@Composable
private fun ChatsScreenSuccessPreview(
    @PreviewParameter(ChatListPreviewParameterProvider::class)
    chats: List<Chat>
) {
    LucheDroidChatTheme {
        ChatsScreen(
            iuState = ChatsListUiState.Success(chats),
            onTryAgainClick = {}
        )
    }
}

@Preview
@Composable
private fun ChatsScreenEmptyListPreview(
    @PreviewParameter(ChatListPreviewParameterProvider::class)
    chats: List<Chat>
) {
    LucheDroidChatTheme {
        ChatsScreen(
            iuState = ChatsListUiState.Success(emptyList()),
            onTryAgainClick = {}
        )
    }
}


@Preview
@Composable
private fun ChatsScreenErrorPreview() {
    LucheDroidChatTheme {
        ChatsScreen(
            iuState = ChatsListUiState.Error,
            onTryAgainClick = {}
        )
    }
}

