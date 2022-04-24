package me.vislavy.vkgram.new_conversation.screens.members_choice.views

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.new_conversation.navigation.NewConversationDestinations
import me.vislavy.vkgram.new_conversation.screens.members_choice.models.MembersChoiceViewState
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@ExperimentalSerializationApi
@Composable
fun MembersChoiceFab(
    viewState: MembersChoiceViewState.Display,
    navController: NavController
) {
    AnimatedVisibility(
        visible = viewState.fabVisible,
        enter = (slideInHorizontally() + fadeIn()),
        exit = (slideOutHorizontally() + fadeOut())
    ) {
        FloatingActionButton(
            onClick = {
                val encodedMembers = Json.encodeToString(
                    viewState.members.map { member ->
                        member.copy(
                            photo = URLEncoder.encode(
                                member.photo,
                                StandardCharsets.UTF_8.toString()
                            )
                        )
                    }
                )
                navController.navigate(
                    route = NewConversationDestinations.ConversationCreation
                            + "/$encodedMembers"
                )
            },
            modifier = Modifier.padding(bottom = 16.dp),
            backgroundColor = VKgramTheme.palette.primary
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = null,
                tint = VKgramTheme.palette.onPrimary
            )
        }
    }
}

@ExperimentalSerializationApi
@Preview
@Composable
fun MembersChoiceFab_Preview() {
    MainTheme {
        MembersChoiceFab(
            viewState = MembersChoiceViewState.Display(
                items = emptyList(),
                members = emptyList(),
                fabVisible = true
            ),
            navController = rememberNavController()
        )
    }
}

@ExperimentalSerializationApi
@Preview
@Composable
fun MembersChoiceDarkFab_Preview() {
    MainTheme(darkThemeEnabled = true) {
        MembersChoiceFab(
            viewState = MembersChoiceViewState.Display(
                items = emptyList(),
                members = emptyList(),
                fabVisible = true
            ),
            navController = rememberNavController()
        )
    }
}