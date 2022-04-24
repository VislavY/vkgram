package me.vislavy.vkgram.new_conversation.screens.conversation_creation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.new_conversation.R
import me.vislavy.vkgram.new_conversation.UserModel

@ExperimentalCoilApi
@Composable
fun MemberItem(
    model: UserModel,
    onRemoveClick: (UserModel) -> Unit
) {
    Surface(color = VKgramTheme.palette.background) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Image(
                    painter = rememberImagePainter(
                        data = model.photo,
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.photo_placeholder_56)
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = "${model.firstName} ${model.lastName}",
                    color = VKgramTheme.palette.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = VKgramTheme.typography.body1
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = model.domain,
                    color = VKgramTheme.palette.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = VKgramTheme.typography.body2
                )
            }

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = {
                    onRemoveClick(model)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = null,
                    tint = VKgramTheme.palette.onPrimary
                )
            }
        }
    }
}


@ExperimentalCoilApi
@Preview
@Composable
fun MemberItem_Preview() {
    MainTheme {
        MemberItem(
            model = UserModel(
                id = 1,
                domain = "Sample domain",
                firstName = "It's",
                lastName = "Sample",
                isSelected = true
            ),
            onRemoveClick = { }
        )
    }
}

@ExperimentalCoilApi
@Preview
@Composable
fun DarkMemberItem_Preview() {
    MainTheme(darkThemeEnabled = true) {
        MemberItem(
            model = UserModel(
                id = 1,
                domain = "Sample domain",
                firstName = "It's",
                lastName = "Sample",
                isSelected = true
            ),
            onRemoveClick = { }
        )
    }
}