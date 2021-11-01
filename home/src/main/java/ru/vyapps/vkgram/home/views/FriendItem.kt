package ru.vyapps.vkgram.home.views

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
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme
import ru.vyapps.vkgram.core.views.ItemButton
import ru.vyapps.vkgram.home.R
import ru.vyapps.vkgram.vk_api.data.User

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
                data = model.photo200Url,
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
                color = VKgramTheme.palette.primaryText,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = VKgramTheme.typography.subtitle1
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text = model.domain,
                color = VKgramTheme.palette.secondaryText,
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