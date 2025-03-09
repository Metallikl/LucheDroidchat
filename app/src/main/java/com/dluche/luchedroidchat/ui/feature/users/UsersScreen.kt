package com.dluche.luchedroidchat.ui.feature.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dluche.luchedroidchat.R
import com.dluche.luchedroidchat.model.User
import com.dluche.luchedroidchat.model.fake.user2
import com.dluche.luchedroidchat.model.fake.user3
import com.dluche.luchedroidchat.model.fake.user4
import com.dluche.luchedroidchat.ui.components.AnimatedContent
import com.dluche.luchedroidchat.ui.components.ChatScaffold
import com.dluche.luchedroidchat.ui.components.ChatTopAppBar
import com.dluche.luchedroidchat.ui.components.GeneralEmptyList
import com.dluche.luchedroidchat.ui.components.GeneralError
import com.dluche.luchedroidchat.ui.components.PrimaryButton
import com.dluche.luchedroidchat.ui.components.UserItem
import com.dluche.luchedroidchat.ui.preview.PagingUsersPreviewParameterProvider
import com.dluche.luchedroidchat.ui.theme.Grey1
import com.dluche.luchedroidchat.ui.theme.LucheDroidChatTheme
import kotlinx.coroutines.flow.flowOf

@ExperimentalMaterial3Api
@Composable
fun UsersRoute(
    viewModel: UsersViewModel = hiltViewModel()
) {
    val pagingUsers = viewModel.users.collectAsLazyPagingItems()
    UsersScreen(pagingUsers)
}

@ExperimentalMaterial3Api
@Composable
fun UsersScreen(pagingUsers: LazyPagingItems<User>) {
    ChatScaffold(
        topBar = {
            ChatTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.feature_users_title)
                    )
                }
            )
        }
    ) {

        when(pagingUsers.loadState.refresh){
            LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            is LoadState.NotLoading -> {
                if(pagingUsers.itemCount == 0){
                    GeneralEmptyList(
                        message = stringResource(R.string.feature_users_empty_list),
                        resourceContent = {
                            AnimatedContent(
                                resId = R.raw.animation_empty_list
                            )
                        }
                    )
                } else {
                    ListContent(pagingUsers)
                }
            }
            is LoadState.Error -> GeneralError(
                title = stringResource(R.string.common_generic_error_title),
                message = stringResource(R.string.common_generic_error_message),
                resourceContent = {
                    AnimatedContent()
                },
                actionContent = {
                    PrimaryButton(
                        text = stringResource(R.string.common_try_again),
                        onClick = {
                            pagingUsers.refresh()
                        }
                    )
                }
            )
        }
    }

}

@Composable
private fun ListContent(pagingUsers: LazyPagingItems<User>) {
    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pagingUsers.itemCount) { index ->
            pagingUsers[index]?.let { user ->
                UserItem(user)
            }
            if (index < pagingUsers.itemCount - 1) {
                HorizontalDivider(
                    modifier = Modifier.padding(top = 16.dp),
                    color = Grey1
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun UsersRouteScreenPreview(
    @PreviewParameter(PagingUsersPreviewParameterProvider::class)
    pagingStates: LoadStates
) {
    val usersFlow = flowOf(
        PagingData.from(
            listOf(
                user2,
                user3,
                user4
            ),
            //Para simular o preview do paging, adicionar o mock do LoadStates atras da properties sourceLoadState
            //sourceLoadStates = pagingStates //comentado pois sempre fic ano cenario de loading
            sourceLoadStates =  LoadStates(
                refresh = LoadState.Error(Throwable()),
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false)
            )
        )
    )
    LucheDroidChatTheme {
        UsersScreen(usersFlow.collectAsLazyPagingItems())
    }
}
