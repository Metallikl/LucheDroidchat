package com.dluche.luchedroidchat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.dluche.luchedroidchat.ui.feature.signin.SignInRoute
import com.dluche.luchedroidchat.ui.feature.signin.SignInScreen
import com.dluche.luchedroidchat.ui.feature.splash.SplashRoute
import kotlinx.serialization.Serializable

@Serializable
object SplashRoute

@Serializable
object SignInRoute

@Serializable
object SignUpRoute

@Composable
fun ChatNavHost() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashRoute) {
        composable<SplashRoute> {
            SplashRoute(
                onNavigateToSignIn = {
                    //Navega para route e limpa a pilha até encontrar a tela SplashRoute e inclusive
                    //remova SplashRoute da pilha
                    navController.navigate(
                        route = SignInRoute,
                        navOptions = navOptions {
                            popUpTo(SplashRoute) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }
        composable<SignInRoute> {
            SignInRoute(
                navigateToSignUp = {
                    navController.navigate(SignUpRoute)
                }
            )
        }
        composable<SignUpRoute> {

        }
    }
}