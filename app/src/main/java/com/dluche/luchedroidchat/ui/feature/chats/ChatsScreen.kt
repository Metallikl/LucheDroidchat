@file:OptIn(ExperimentalMaterial3Api::class)

package com.dluche.luchedroidchat.ui.feature.chats

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dluche.luchedroidchat.ui.components.ChatItem
import com.dluche.luchedroidchat.ui.theme.Grey1
import com.dluche.luchedroidchat.ui.theme.LucheDroidChatTheme

@Composable
fun ChatsRoute() {
    ChatsScreen()
}

@Composable
fun ChatsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ola, Dev")
                }
            )
        }
    ){ paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp)
            
        ) {
            items(100){
                ChatItem()
                HorizontalDivider(
                    color = Grey1
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChatsScreenPreview() {
    LucheDroidChatTheme{
        ChatsScreen()
    }
}
