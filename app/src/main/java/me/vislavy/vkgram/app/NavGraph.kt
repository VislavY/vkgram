package me.vislavy.vkgram.app

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi
import me.vislavy.vkgram.app_settings.AppSettingsScreen
import me.vislavy.vkgram.app_settings.AppSettingsViewModel
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.home.HomeScreen
import me.vislavy.vkgram.home.HomeViewModel
import me.vislavy.vkgram.login.LoginScreen
import me.vislavy.vkgram.message_history.MessageHistoryScreen
import me.vislavy.vkgram.message_history.MessageHistoryViewModel
import me.vislavy.vkgram.new_conversation.navigation.newConversationGraph
import me.vislavy.vkgram.profile.ProfileScreen
import me.vislavy.vkgram.profile.ProfileViewModel
import me.vislavy.vkgram.search.SearchScreen
import me.vislavy.vkgram.search.SearchViewModel

@OptIn(ExperimentalAnimationApi::class)
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
            LoginScreen(navController = navController)
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
            route = "${Destinations.MessageHistory}/{conversationId}",
            arguments = listOf(navArgument("conversationId") { type = NavType.IntType }),
            enterTransition = {
                slideInHorizontally(tween(200))
            },
            popExitTransition = {
                slideOutHorizontally(tween(200))
            }
        ) { backStackEntry ->
            val conversationId = backStackEntry.arguments?.getInt("conversationId") ?: return@composable
            val viewModel: MessageHistoryViewModel = hiltViewModel()
            MessageHistoryScreen(
                conversationId = conversationId,
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = "${Destinations.Profile}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType }),
            enterTransition = {
                scaleIn(initialScale = 0.95f)
            },
            popExitTransition = {
                scaleOut(targetScale = 0.95f)
            }
        ) { backStackEntry ->
            val userId = backStackEntry.arguments!!.getLong("userId")
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                userId = userId,
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Destinations.Search) {
            val viewModel: SearchViewModel = hiltViewModel()
            SearchScreen(navController, viewModel)
        }

        composable(Destinations.AppSettings) {
            val viewModel: AppSettingsViewModel = hiltViewModel()
            AppSettingsScreen(viewModel, navController)
        }
    }
}