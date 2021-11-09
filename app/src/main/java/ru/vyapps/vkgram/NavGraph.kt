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
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.EntryPointAccessors
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.vyapps.vkgram.core.ConversationModel
import ru.vyapps.vkgram.core.Destinations
import ru.vyapps.vkgram.home.HomeScreen
import ru.vyapps.vkgram.home.HomeViewModel
import ru.vyapps.vkgram.login.LoginScreen
import ru.vyapps.vkgram.message_history.MessageHistoryScreen
import ru.vyapps.vkgram.message_history.MessageHistoryViewModel
import ru.vyapps.vkgram.new_conversation.navigation.newConversationGraph
import ru.vyapps.vkgram.profile.ProfileScreen
import ru.vyapps.vkgram.profile.ProfileViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

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
    val sharedPreferences =
        activity.getSharedPreferences(stringResource(R.string.pref_file_key), Context.MODE_PRIVATE)
    val token =
        sharedPreferences.getString(activity.getString(R.string.access_token_pref_key), null)
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
            route = Destinations.Login,
            exitTransition = {
                fadeOut(animationSpec = tween(400))
            }
        ) {
            LoginScreen(navController)
        }

        composable(
            route = Destinations.Home,
            enterTransition = {
                null
            },
            exitTransition = {
                null
            }
        ) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(navController, viewModel)
        }

        composable(
            route = "${Destinations.MessageHistory}/{conversation}",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 200 },
                    animationSpec = tween(200)
                ) + fadeIn(animationSpec = tween(400))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 200 },
                    animationSpec = tween(200)
                ) + fadeOut(animationSpec = tween(400))
            }
        ) { backStackEntry ->
            val encodedConversation =
                backStackEntry.arguments?.getString("conversation") ?: return@composable
            val conversation = Json.decodeFromString<ConversationModel>(encodedConversation)
            val viewModel: MessageHistoryViewModel = hiltViewModel()
            MessageHistoryScreen(
                conversation = conversation.copy(
                    photo = URLDecoder.decode(
                        conversation.photo,
                        StandardCharsets.UTF_8.toString()
                    )
                ),
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = Destinations.Profile,
            enterTransition = {
                scaleIn(initialScale = 0.95f)
            },
            popExitTransition = {
                scaleOut(targetScale = 0.95f)
            }
        ) {
            ProfileScreen(navController, profileViewModel(accessToken()))
        }
    }
}