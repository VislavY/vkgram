package ru.vyapps.vkgram

import android.app.Activity
import android.content.Context
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.EntryPointAccessors
import ru.vyapps.vkgram.conversations.ConversationsScreen
import ru.vyapps.vkgram.conversations.ConversationsViewModel
import ru.vyapps.vkgram.login.LoginScreen
import ru.vyapps.vkgram.message_history.MessageHistoryViewModel
import ru.vyapps.vkgram.message_history.MessageHistoryScreen

object Destinations {
    const val LOGIN_SCREEN = "login_screen"
    const val CONVERSATION_LIST_SCREEN = "conversations_screen"
    const val MESSAGE_HISTORY_SCREEN= "message_history_screen"
}

@Composable
fun rememberConversationsViewModel(accessToken: String): ConversationsViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).provideConversationsViewModelFactory()

    return viewModel(
        factory = ConversationsViewModel.provideFactory(factory, accessToken)
    )
}

@Composable
fun rememberMessageHistoryViewModel(
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
fun rememberAccessToken(): String {
    val activity = (LocalContext.current as Activity)
    val preferences = activity.getPreferences(Context.MODE_PRIVATE)
    val token = preferences.getString(activity.getString(R.string.token_pref_key), null)
    return if (token.isNullOrBlank()) "" else token
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(
            route = Destinations.LOGIN_SCREEN,
            exitTransition = {_, _ ->
                fadeOut(animationSpec = tween(400))
            }
        ) {
            LoginScreen(navController)
        }

        composable(
            route = Destinations.CONVERSATION_LIST_SCREEN,
            enterTransition = { _, _ ->
                fadeIn(animationSpec = tween(0))
            },
            exitTransition = { _, _ ->
                fadeOut(animationSpec = tween(0))
            }
        ) {
            ConversationsScreen(navController, rememberConversationsViewModel(rememberAccessToken()))
        }

        composable(
            route = "${Destinations.MESSAGE_HISTORY_SCREEN}/{conversationType}/{conversationId}",
            arguments = listOf(navArgument("conversationId") { type = NavType.LongType }),
            enterTransition = { _, _ ->
                slideInHorizontally(
                    initialOffsetX = { 200 },
                    animationSpec = tween(200)
                ) + fadeIn(animationSpec = tween(400))
            },
            popExitTransition = { _, _ ->
                slideOutHorizontally(
                    targetOffsetX = { 200 },
                    animationSpec = tween(200)
                ) + fadeOut(animationSpec = tween(400))
            }
        ) { backStackEntry ->
            backStackEntry.arguments?.let { args ->
                val conversationType = args.getString("conversationType", "user")
                val conversationId = args.getLong("conversationId", 386070111)
                MessageHistoryScreen(navController, rememberMessageHistoryViewModel(conversationId, rememberAccessToken()))
            }
        }
    }
}