package me.vislavy.vkgram.new_conversation.screens.conversation_creation.views

import androidx.compose.foundation.layout.PaddingValues
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
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun ConversationCreationTopBar(
    modifier: Modifier = Modifier,
    color: Color = VKgramTheme.palette.primary,
    contentPadding: PaddingValues = rememberInsetsPaddingValues(
        insets = LocalWindowInsets.current.systemBars,
        applyBottom = false
    ),
    navController: NavController
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = color,
        contentPadding = contentPadding
    ) {
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
fun PreviewCreateConversationTopBar() {
    MainTheme {
        ConversationCreationTopBar(navController = rememberNavController())
    }
}