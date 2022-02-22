package me.vislavy.vkgram.new_conversation.navigation

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
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.new_conversation.UserModel
import me.vislavy.vkgram.new_conversation.screens.conversation_creation.ConversationCreationScreen
import me.vislavy.vkgram.new_conversation.screens.conversation_creation.ConversationCreationViewModel
import me.vislavy.vkgram.new_conversation.screens.members_choice.MembersChoiceScreen
import me.vislavy.vkgram.new_conversation.screens.members_choice.MembersChoiceViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalSerializationApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
fun NavGraphBuilder.newConversationGraph(navController: NavController) {
    navigation(NewConversationDestinations.MemberChoice, Destinations.NewConversation) {
        composable(
            route = NewConversationDestinations.MemberChoice,
            enterTransition = {
                when (initialState.destination.route) {
                    Destinations.Home -> scaleIn(initialScale = 0.95f)
                    else -> null
                }
            },
            popExitTransition = {
                scaleOut(targetScale = 0.95f)
            }
        ) {
            val viewModel = hiltViewModel<MembersChoiceViewModel>()
            MembersChoiceScreen(navController, viewModel)
        }

        composable(
            route = "${NewConversationDestinations.ConversationCreation}/{members}",
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