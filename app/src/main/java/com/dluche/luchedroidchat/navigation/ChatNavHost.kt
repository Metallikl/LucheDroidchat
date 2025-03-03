package com.dluche.luchedroidchat.navigation

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.dluche.luchedroidchat.ui.feature.chats.ChatsRoute
import com.dluche.luchedroidchat.ui.feature.chats.navigateToChats
import com.dluche.luchedroidchat.ui.feature.signin.SignInRoute
import com.dluche.luchedroidchat.ui.feature.signup.SignUpRoute
import com.dluche.luchedroidchat.ui.feature.splash.SplashRoute
import com.dluche.luchedroidchat.ui.feature.users.UsersRoute

@Composable
fun ChatNavHost(
    navigationState: LucheDroidChatNavigationState
) {
    val navController = navigationState.navController
    val activity = LocalActivity.current as Activity

    NavHost(navController = navController, startDestination = Route.SplashRoute) {
        composable<Route.SplashRoute> {
            SplashRoute(
                onNavigateToSignIn = {
                    navController.navigate(
                        route = Route.SignInRoute,
                        navOptions = navOptions {
                            popUpTo(Route.SplashRoute) {
                                inclusive = true
                            }
                        }
                    )
                },
                onNavigateToMain = {
                    navController.navigateToChats(
                        navOptions = navOptions {
                            popUpTo(Route.SplashRoute) {
                                inclusive = true
                            }
                        }
                    )
                },
                onCloseApp = {
                    activity.finish()
                }
            )
        }
        composable<Route.SignInRoute>(
            enterTransition = {
                slideInTo(AnimatedContentTransitionScope.SlideDirection.Right)
            },
            exitTransition = {
                slideOutTo(AnimatedContentTransitionScope.SlideDirection.Left)
            }
        ) {
            SignInRoute(
                navigateToSignUp = {
                    navController.navigate(Route.SignUpRoute)
                },
                navigateToMain = {
                    navController.navigateToChats(
                        navOptions = navOptions {
                            popUpTo(Route.SignUpRoute) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }
        composable<Route.SignUpRoute>(
            enterTransition = {
                slideInTo(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutTo(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            SignUpRoute(
                onSignUpSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable<Route.ChatsRoute>() {
            ChatsRoute()
        }

        composable<Route.UsersRoute> {
            UsersRoute()
        }
    }
}