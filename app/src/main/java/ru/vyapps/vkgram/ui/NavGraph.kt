package ru.vyapps.vkgram.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.vyapps.vkgram.ui.conversations.ConversationsScreen
import ru.vyapps.vkgram.ui.login.LoginScreen
import ru.vyapps.vkgram.ui.login.LoginViewModel

object Destinations {
    const val LOGIN_SCREEN = "login_screen"
    const val CONVERSATIONS_SCREEN = "conversations_screen"
}

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.LOGIN_SCREEN) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(viewModel)
        }

        composable(Destinations.CONVERSATIONS_SCREEN) {
            ConversationsScreen()
        }
    }
}