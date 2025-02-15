package com.dluche.luchedroidchat.navigation

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.dluche.luchedroidchat.ui.feature.signin.SignInRoute
import com.dluche.luchedroidchat.ui.feature.signup.SignUpRoute
import com.dluche.luchedroidchat.ui.feature.splash.SplashRoute
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    object SplashRoute

    @Serializable
    object SignInRoute

    @Serializable
    object SignUpRoute
}


@Composable
fun ChatNavHost() {
    val navController = rememberNavController()
    val activity = LocalContext.current as? Activity


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
                    Toast.makeText(
                        navController.context,
                        "Navigate to main",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onCloseApp = {
                    activity?.finish()
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
                    Toast.makeText(
                        navController.context,
                        "Navigate to main",
                        Toast.LENGTH_SHORT
                    ).show()
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
    }
}