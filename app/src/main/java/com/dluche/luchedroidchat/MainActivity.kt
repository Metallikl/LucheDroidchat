package com.dluche.luchedroidchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dluche.luchedroidchat.ui.ChatApp
import com.dluche.luchedroidchat.ui.theme.LucheDroidChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LucheDroidChatTheme {
               ChatApp()
            }
        }
    }
}
