package ru.vyapps.vkgram.new_conversation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.vyapps.vkgram.core.Destinations
import ru.vyapps.vkgram.new_conversation.UserModel
import ru.vyapps.vkgram.new_conversation.screens.conversation_creation.ConversationCreationScreen
import ru.vyapps.vkgram.new_conversation.screens.conversation_creation.ConversationCreationViewModel
import ru.vyapps.vkgram.new_conversation.screens.members_choice.MembersChoiceScreen
import ru.vyapps.vkgram.new_conversation.screens.members_choice.MembersChoiceViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalSerializationApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
fun NavGraphBuilder.newConversationGraph(navController: NavController) {
    navigation(NewConversationDestinations.MEMBERS_CHOICE, Destinations.NEW_CONVERSATION_SCREEN) {
        composable(
            route = NewConversationDestinations.MEMBERS_CHOICE,
            enterTransition = { _, _ ->
                fadeIn(animationSpec = tween(500))
            },
            popExitTransition = { _, _ ->
                fadeOut(animationSpec = tween(500))
            }
        ) {
            val viewModel = hiltViewModel<MembersChoiceViewModel>()
            MembersChoiceScreen(navController, viewModel)
        }

        composable(
            route = "${NewConversationDestinations.CONVERSATION_CREATION}/{members}",
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
            val encodedMembers = backStackEntry.arguments?.getString("members") ?: return@composable
            val members = Json.decodeFromString<List<UserModel>>(encodedMembers).map { member ->
                member.copy(
                    photo = URLDecoder.decode(member.photo, StandardCharsets.UTF_8.toString())
                )
            }
            val viewModel = hiltViewModel<ConversationCreationViewModel>()
            ConversationCreationScreen(members, navController, viewModel)
        }
    }
}