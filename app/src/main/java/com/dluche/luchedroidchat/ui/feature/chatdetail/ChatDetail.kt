package com.dluche.luchedroidchat.ui.feature.chatdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.model.ChatMessage
import com.dluche.luchedroidchat.model.fake.chatMessage1
import com.dluche.luchedroidchat.model.fake.chatMessage2
import com.dluche.luchedroidchat.model.fake.chatMessage3
import com.dluche.luchedroidchat.model.fake.chatMessage4
import com.dluche.luchedroidchat.model.fake.chatMessage5
import com.dluche.luchedroidchat.ui.components.AnimatedContent
import com.dluche.luchedroidchat.ui.components.ChatMessageBubble
import com.dluche.luchedroidchat.ui.components.ChatMessageTextField
import com.dluche.luchedroidchat.ui.components.ChatScaffold
import com.dluche.luchedroidchat.ui.components.ChatTopAppBar
import com.dluche.luchedroidchat.ui.components.GeneralEmptyList
import com.dluche.luchedroidchat.ui.components.GeneralError
import com.dluche.luchedroidchat.ui.components.PrimaryButton
import com.dluche.luchedroidchat.ui.components.RoundedAvatar
import com.dluche.luchedroidchat.ui.theme.LucheDroidChatTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun ChatDetailRoute(
    viewModel: ChatDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val pagingChatMessages = viewModel.pagingChatMessage.collectAsLazyPagingItems()
    val messageText = viewModel.messageText

    ChatDetailScreen(
        pagingChatMessages = pagingChatMessages,
        messageText = messageText,
        onMessageChange = viewModel::dispatchEvent,
        onSendClicked = viewModel::dispatchEvent,
        onNavigationClicked = navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    pagingChatMessages: LazyPagingItems<ChatMessage>,
    messageText: String,
    onMessageChange: (ChatDetailsEvents) -> Unit,
    onSendClicked: (ChatDetailsEvents) -> Unit,
    onNavigationClicked: () -> Unit
) {
    ChatScaffold(
        topBar = {
            ChatTopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier
                            .clickable(
                                onClick = onNavigationClicked
                            )
                    )
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        RoundedAvatar(
                            imageUri = null,
                            contentDescription = null,
                            size = 42.dp
                        )

                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Joao",
                                color = MaterialTheme.colorScheme.inverseOnSurface,
                                style = MaterialTheme.typography.titleMedium,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )


                            Text(
                                text = "Online",
                                color = MaterialTheme.colorScheme.inverseOnSurface,
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                    }

                }
            )
        }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                //Refresh estado inicial de abertura de tela ou refresh
                when (pagingChatMessages.loadState.refresh) {
                    LoadState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }

                    is LoadState.NotLoading -> {
                        if (pagingChatMessages.itemCount == 0) {
                            GeneralEmptyList(
                                message = stringResource(R.string.feature_chat_detail_empty_list),
                                resourceContent = {
                                    AnimatedContent(
                                        resId = R.raw.animation_empty_list
                                    )
                                }
                            )
                        } else {
                            ChatListContent(pagingChatMessages)
                        }
                    }

                    is LoadState.Error -> {
                        GeneralError(
                            title = stringResource(R.string.common_generic_error_title),
                            message = stringResource(R.string.common_generic_error_message),
                            resourceContent = {
                                AnimatedContent()
                            },
                            actionContent = {
                                PrimaryButton(
                                    text = stringResource(R.string.common_try_again),
                                    onClick = {
                                        pagingChatMessages.refresh()
                                    }
                                )
                            }
                        )
                    }
                }

            }
            ChatMessageTextField(
                value = messageText,
                onInputChange = {
                    onMessageChange(ChatDetailsEvents.OnMessageChange(it))
                },
                onSendClicked = {
                    onSendClicked(ChatDetailsEvents.OnSendMessage)
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp, top = 8.dp),
                placeholder = stringResource(R.string.feature_chat_detail_text_field_placeholder)
            )
        }

    }

}

@Composable
private fun ChatListContent(pagingChatMessages: LazyPagingItems<ChatMessage>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        reverseLayout = true,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom)
    ) {
        items(pagingChatMessages.itemCount) { index ->
            val chatMessage = pagingChatMessages[index]
            val previousChatMessage = if (index > 0) {
                pagingChatMessages[index - 1]
            } else null

            if (chatMessage != null) {
                ChatMessageBubble(
                    chatMessage = chatMessage,
                    previousChatMessage = previousChatMessage
                )
            }
        }

        if (pagingChatMessages.loadState.append is LoadState.Loading) {
            item {
                AppendingLoadingState()
            }
        }

        if (pagingChatMessages.loadState.append is LoadState.Error) {
            item {
                AppendingErrorState(pagingChatMessages)
            }
        }
    }
}

@Composable
private fun AppendingLoadingState() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun AppendingErrorState(pagingChatMessages: LazyPagingItems<ChatMessage>) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.feature_chat_detail_error_loading_more),
            modifier = Modifier
                .padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.error
        )

        PrimaryButton(
            text = stringResource(R.string.common_try_again),
            onClick = {
                pagingChatMessages.retry()
            },
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .height(46.dp)
        )
    }
}

@Preview
@Composable
private fun ChatDetailScreenPreview() {
    val pagingChatMessages = flowOf(
        PagingData.from(
            listOf(
                chatMessage5,
                chatMessage4,
                chatMessage3,
                chatMessage2,
                chatMessage1
            ),
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(false),
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false)
            )
        )
    ).collectAsLazyPagingItems()

    LucheDroidChatTheme {
        ChatDetailScreen(
            pagingChatMessages = pagingChatMessages,
            messageText = "",
            onMessageChange = {},
            onSendClicked = {},
            onNavigationClicked = {}
        )
    }
}
