package com.dluche.luchedroidchat.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dluche.luchedroidchat.navigation.ChatNavHost
import com.dluche.luchedroidchat.navigation.rememberDroidChatNavigationState

@Composable
fun ChatApp() {
    val navigationState = rememberDroidChatNavigationState()
    Scaffold(
        bottomBar = {
            //BottomBar()
        }
    ) { innerPaddings ->
        Box(
            modifier = Modifier
                .consumeWindowInsets(innerPaddings)
                .padding(paddingValues = innerPaddings)
                .imePadding()//considera padding quando o teclado aparece, empurrando layout pra cima
                .fillMaxSize()
        ){
            ChatNavHost(navigationState = navigationState)
        }
    }
}
