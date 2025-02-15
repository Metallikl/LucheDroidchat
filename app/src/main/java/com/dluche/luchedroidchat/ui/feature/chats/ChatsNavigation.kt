package com.dluche.luchedroidchat.ui.feature.chats

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.dluche.luchedroidchat.navigation.Route

fun NavController.navigateToChats(
    navOptions: NavOptions? = null
){
    this.navigate(Route.ChatsRoute,navOptions)
}