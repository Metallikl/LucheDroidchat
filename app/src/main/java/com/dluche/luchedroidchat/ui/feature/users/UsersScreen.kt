package com.dluche.luchedroidchat.ui.feature.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dluche.luchedroidchat.model.User
import com.dluche.luchedroidchat.model.fake.user2
import com.dluche.luchedroidchat.model.fake.user3
import com.dluche.luchedroidchat.model.fake.user4
import com.dluche.luchedroidchat.ui.theme.LucheDroidChatTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun UsersRoute(
    viewModel: UsersViewModel = hiltViewModel()
) {
    val pagingUsers = viewModel.users.collectAsLazyPagingItems()
    UsersScreen(pagingUsers)
}

@Composable
fun UsersScreen(pagingUsers: LazyPagingItems<User>) {

    Scaffold {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pagingUsers.itemCount) { index ->
                pagingUsers[index]?.let { user ->
                    Text(text = user.firstName)
                }
            }
        }
    }

}

@Preview
@Composable
private fun UsersRouteScreenPreview() {
    val usersFlow = flowOf(
        PagingData.from(
            listOf(
                user2,
                user3,
                user4
            )
        )
    )
    LucheDroidChatTheme {
        UsersScreen(usersFlow.collectAsLazyPagingItems())
    }
}
