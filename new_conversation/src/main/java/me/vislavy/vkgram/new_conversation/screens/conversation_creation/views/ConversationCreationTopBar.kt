package me.vislavy.vkgram.new_conversation.screens.conversation_creation.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun ConversationCreationTopBar(navController: NavController) {
    TopAppBar(backgroundColor = Color.White) {
        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                tint = VKgramTheme.palette.secondary
            )
        }

        Spacer(Modifier.width(16.dp))

        Text(
            text = "Новая беседа",
            color = VKgramTheme.palette.primaryText,
            style = VKgramTheme.typography.topBarTitle
        )
    }
}

@Preview
@Composable
fun ConversationCreationTopBar_Preview() {
    MainTheme() {
        ConversationCreationTopBar(rememberNavController())
    }
}

@Preview
@Composable
fun DarkConversationCreationTopBar_Preview() {
    MainTheme(darkThemeEnabled = true) {
        ConversationCreationTopBar(rememberNavController())
    }
}