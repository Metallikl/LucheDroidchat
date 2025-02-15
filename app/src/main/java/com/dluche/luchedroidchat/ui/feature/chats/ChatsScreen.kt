@file:OptIn(ExperimentalMaterial3Api::class)

package com.dluche.luchedroidchat.ui.feature.chats

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                .fillMaxSize()
        ) {
            items(100){
                Text(
                    text = "Chat $it"
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
