package ru.vyapps.vkgram

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import dagger.hilt.android.EntryPointAccessors
import ru.vyapps.vkgram.conversations.ConversationsScreen
import ru.vyapps.vkgram.conversations.ConversationsViewModel
import ru.vyapps.vkgram.login.LoginScreen
import ru.vyapps.vkgram.message_history.MessageHistoryViewModel
import ru.vyapps.vkgram.message_history.MessageHistoryScreen

object Destinations {
    const val LOGIN_SCREEN = "login_screen"
    const val CONVERSATION_LIST_SCREEN = "conversation_list_screen"
    const val MESSAGE_HISTORY_SCREEN= "message_history_screen"
}

// TODO Anti-pattern?
@Composable
fun conversationsViewModel(accessToken: String): ConversationsViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).provideConversationsViewModelFactory()

    return viewModel(
        factory = ConversationsViewModel.provideFactory(factory, accessToken)
    )
}

@Composable
fun messageHistoryViewModel(
    conversationId: Long,
    token: String
): MessageHistoryViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).provideMessageHistoryViewModelFactory()

    return viewModel(
        factory = MessageHistoryViewModel.provideFactory(
            factory,
            conversationId,
            token
        )
    )
}

@Composable
fun rememberToken(): String {
    val activity = (LocalContext.current as Activity)
    val preferences = activity.getPreferences(Context.MODE_PRIVATE)
    val token = preferences.getString(activity.getString(R.string.token_pref_key), null)
    return if (token.isNullOrBlank()) "" else token
}

@ExperimentalCoilApi
@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Destinations.LOGIN_SCREEN) {
            LoginScreen(navController)
        }

        composable(Destinations.CONVERSATION_LIST_SCREEN) {
            ConversationsScreen(navController, conversationsViewModel(rememberToken()))
        }

        composable(
            route = "${Destinations.MESSAGE_HISTORY_SCREEN}/{conversationType}/{conversationId}",
            arguments = listOf(navArgument("conversationId") { type = NavType.LongType })
        ) { backStackEntry ->
            backStackEntry.arguments?.let { args ->
                val conversationType = args.getString("conversationType", "user")
                val conversationId = args.getLong("conversationId", 386070111)
                MessageHistoryScreen(navController, messageHistoryViewModel(conversationId, rememberToken()))
            }
        }
    }
}