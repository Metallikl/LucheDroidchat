package com.dluche.luchedroidchat.ui.feature.users

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.dluche.luchedroidchat.navigation.Route

fun NavController.navigateToUsers(
    navOptions: NavOptions? = null
){
    this.navigate(Route.UsersRoute,navOptions)
}