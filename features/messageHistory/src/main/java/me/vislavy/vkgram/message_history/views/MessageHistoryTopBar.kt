package me.vislavy.vkgram.message_history.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
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
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.message_history.R
import me.vislavy.vkgram.message_history.models.MessageHistoryViewState

@Composable
fun MessageHistoryTopBar(
    modifier: Modifier = Modifier,
    viewState: MessageHistoryViewState.Display,
    color: Color = VKgramTheme.palette.primary,
    navController: NavController
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = color
    ) {
        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                tint = VKgramTheme.palette.onPrimary
            )
        }

        Spacer(Modifier.width(16.dp))

        Image(
            painter = rememberImagePainter(
                data = viewState.conversation?.photo,
                builder = {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.photo_placeholder_42)
                }
            ),
            contentDescription = null,
            modifier = Modifier.size(42.dp)
        )

        Spacer(Modifier.width(16.dp))

        Column {
            Text(
                text = viewState.conversation?.title.toString(),
                maxLines = 1,
                color = VKgramTheme.palette.primaryText,
                style = VKgramTheme.typography.subtitle1
            )

            Text(
                text = viewState.topBarSubtitle,
                color = VKgramTheme.palette.secondaryText,
                style = VKgramTheme.typography.body2
            )
        }
    }
}

@Preview
@Composable
fun MessageHistoryTopBar_Preview() {
    MainTheme {
        MessageHistoryTopBar(
            viewState = MessageHistoryViewState.Display(),
            navController = rememberNavController()
        )
    }
}