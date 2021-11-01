package ru.vyapps.vkgram

import android.app.Activity
import android.content.Context
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.EntryPointAccessors
import kotlinx.serialization.ExperimentalSerializationApi
import ru.vyapps.vkgram.core.Destinations
import ru.vyapps.vkgram.home.HomeScreen
import ru.vyapps.vkgram.home.HomeViewModel
import ru.vyapps.vkgram.login.LoginScreen
import ru.vyapps.vkgram.message_history.MessageHistoryViewModel
import ru.vyapps.vkgram.message_history.MessageHistoryScreen
import ru.vyapps.vkgram.new_conversation.navigation.newConversationGraph
import ru.vyapps.vkgram.profile.ProfileScreen
import ru.vyapps.vkgram.profile.ProfileViewModel

@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalSerializationApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun messageHistoryViewModel(
    conversationId: Int,
    conversationType: String,
    accessToken: String
): MessageHistoryViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).provideMessageHistoryViewModelFactory()

    return viewModel(
        factory = MessageHistoryViewModel.provideFactory(
            factory = factory,
            conversationId = conversationId,
            conversationType = conversationType,
            accessToken = accessToken
        )
    )
}

@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalSerializationApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun profileViewModel(accessToken: String): ProfileViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).provideProfileViewModelFactory()

    return viewModel(factory = ProfileViewModel.provideFactory(factory, accessToken))
}

@Composable
fun accessToken(): String {
    val activity = (LocalContext.current as Activity)
    val sharedPreferences = activity.getSharedPreferences(stringResource(R.string.pref_file_key), Context.MODE_PRIVATE)
    val token = sharedPreferences.getString(activity.getString(R.string.access_token_pref_key), null)
    return if (token.isNullOrBlank()) "" else token
}

@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalSerializationApi
@ExperimentalPagerApi
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
        newConversationGraph(navController)

        composable(
            route = Destinations.LOGIN_SCREEN,
            exitTransition = {_, _ ->
                fadeOut(animationSpec = tween(400))
            }
        ) {
            LoginScreen(navController)
        }

        composable(
            route = Destinations.HOME_SCREEN,
            enterTransition = { _, _ ->
                null
            },
            exitTransition = { _, _ ->
                null
            }
        ) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(navController, viewModel)
        }

        composable(
            route = "${Destinations.MESSAGE_HISTORY_SCREEN}/{conversationId}/{conversationType}",
            arguments = listOf(
                navArgument("conversationId") {
                    type = NavType.IntType
                }
            ),
            enterTransition = { _, _ ->
                slideInHorizontally(
                    initialOffsetX = { 200 },
                    animationSpec = tween(200)
                ) + fadeIn(animationSpec = tween(500))
            },
            popExitTransition = { _, _ ->
                slideOutHorizontally(
                    targetOffsetX = { 200 },
                    animationSpec = tween(200)
                ) + fadeOut(animationSpec = tween(500))
            }
        ) { backStackEntry ->
            backStackEntry.arguments?.let { args ->
                val conversationId = args.getInt("conversationId")
                val conversationType = args.getString("conversationType", "")
                MessageHistoryScreen(
                    navController = navController,
                    messageHistoryViewModel(
                        conversationId = conversationId,
                        conversationType = conversationType,
                        accessToken = accessToken()
                    )
                )
            }
        }

        composable(
            route = Destinations.PROFILE_SCREEN,
            enterTransition = { _, _ ->
                fadeIn(animationSpec = tween(500))
            },
            popExitTransition = { _, _ ->
                fadeOut(animationSpec = tween(500))
            }
        ) {
            ProfileScreen(navController, profileViewModel(accessToken()))
        }
    }
}