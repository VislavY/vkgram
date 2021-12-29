package me.vislavy.vkgram.app

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.home.HomeScreen
import me.vislavy.vkgram.home.HomeViewModel
import me.vislavy.vkgram.login.LoginScreen
import me.vislavy.vkgram.login.LoginViewModel
import me.vislavy.vkgram.message_history.MessageHistoryScreen
import me.vislavy.vkgram.message_history.MessageHistoryViewModel
import me.vislavy.vkgram.new_conversation.navigation.newConversationGraph
import me.vislavy.vkgram.profile.ProfileScreen
import me.vislavy.vkgram.profile.ProfileViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

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
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(navController = navController, viewModel = viewModel)
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
            route = "${Destinations.Profile}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType }),
            enterTransition = {
                scaleIn(initialScale = 0.95f)
            },
            popExitTransition = {
                scaleOut(targetScale = 0.95f)
            }
        ) { backStackEntry ->
            val userId = backStackEntry.arguments!!.getInt("userId")
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                userId = userId,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}