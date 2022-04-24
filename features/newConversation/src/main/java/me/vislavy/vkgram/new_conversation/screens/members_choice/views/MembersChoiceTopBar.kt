package me.vislavy.vkgram.new_conversation.screens.members_choice.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun MembersChoiceTopBar(navController: NavController) {
    TopAppBar(backgroundColor = VKgramTheme.palette.background) {
        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null,
                tint = VKgramTheme.palette.onPrimary
            )
        }

        Spacer(Modifier.width(16.dp))

        Text(
            text = "Новая беседа",
            color = VKgramTheme.palette.onSurface,
            style = VKgramTheme.typography.topBarTitle
        )

        Spacer(Modifier.weight(1f))

        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = VKgramTheme.palette.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun MembersChoiceTopBar_Preview() {
    MainTheme {
        MembersChoiceTopBar(rememberNavController())
    }
}

@Preview
@Composable
fun MembersChoiceDarkTopBar_Preview() {
    MainTheme(darkThemeEnabled = true) {
        MembersChoiceTopBar(rememberNavController())
    }
}