package ru.vyapps.vkgram.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import ru.vyapps.vkgram.ui.conversations.ConversationsScreen
import ru.vyapps.vkgram.ui.conversations.ConversationsViewModel
import ru.vyapps.vkgram.ui.login.LoginScreen
import ru.vyapps.vkgram.ui.login.LoginViewModel
import ru.vyapps.vkgram.ui.messages.MessagesScreen

object Destinations {
    const val LOGIN_SCREEN = "login_screen"
    const val CONVERSATIONS_SCREEN = "conversations_screen"
    const val MESSAGE_HISTORY_SCREEN= "message_history_screen"
}

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.LOGIN_SCREEN) {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(viewModel)
        }

        composable(Destinations.CONVERSATIONS_SCREEN) {
            val viewModel: ConversationsViewModel = hiltViewModel()
            ConversationsScreen(viewModel, navController)
        }

        composable(
            route = "${Destinations.MESSAGE_HISTORY_SCREEN}/{conversationType}/{conversationId}",
            arguments = listOf(navArgument("conversationId") { type = NavType.LongType })
        ) { backStackEntry ->
            backStackEntry.arguments?.let { args ->
                val conversationType = args.getString("conversationType", "user")
                val conversationId = args.getLong("conversationId", 386070111)
                MessagesScreen(
                    navController = navController,
                    conversationType = conversationType,
                    conversationId = conversationId
                )
            }
        }
    }
}