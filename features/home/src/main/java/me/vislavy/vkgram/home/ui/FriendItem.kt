package me.vislavy.vkgram.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.views.ItemButton
import me.vislavy.vkgram.api.data.User
import me.vislavy.vkgram.home.R

@Composable
fun FriendItem(
    model: User,
    onClick: (User) -> Unit
) {
    ItemButton(
        onClick = {
            onClick(model)
        }
    ) {
        Image(
            painter = rememberImagePainter(
                data = model.photoUrl,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.photo_placeholder_56)
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )

        Spacer(Modifier.width(16.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = "${model.firstName} ${model.lastName}",
                color = VKgramTheme.palette.onSurface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = VKgramTheme.typography.subtitle1
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text = model.domain,
                color = VKgramTheme.palette.onSurface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = VKgramTheme.typography.body1
            )
        }
    }
}

@Preview
@Composable
fun FriendItem_Preview() {
    MainTheme {
        FriendItem(
            model = User(
                id = 1,
                domain = "Sample domain",
                firstName = "It's",
                lastName = "Sample"
            ),
            onClick = { }
        )
    }
}

@Preview
@Composable
fun DarkFriendItem_Preview() {
    MainTheme(darkThemeEnabled = true) {
        FriendItem(
            model = User(
                id = 1,
                domain = "Sample domain",
                firstName = "It's",
                lastName = "Sample"
            ),
            onClick = { }
        )
    }
}