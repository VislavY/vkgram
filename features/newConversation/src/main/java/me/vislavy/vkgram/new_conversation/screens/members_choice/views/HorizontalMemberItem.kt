package me.vislavy.vkgram.new_conversation.screens.members_choice.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun HorizontalMemberItem(
    model: UserModel,
    onRemoveClick: (UserModel) -> Unit
) {
    Surface(color = VKgramTheme.palette.background) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.size(64.dp)) {
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
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(56.dp)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(
                            color = VKgramTheme.palette.background,
                            shape = CircleShape
                        )
                        .size(22.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            model.isSelected = false
                            onRemoveClick(model)
                        },
                        modifier = Modifier
                            .background(
                                color = VKgramTheme.palette.surface,
                                shape = CircleShape
                            )
                            .size(18.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            tint = VKgramTheme.palette.onSurface
                        )
                    }
                }
            }

            Spacer(Modifier.height(0.dp))

            Text(
                text = model.firstName,
                color = VKgramTheme.palette.onSurface,
                style = VKgramTheme.typography.body2
            )
        }
    }
}

@ExperimentalCoilApi
@Preview
@Composable
fun HorizontalMemberItem_Preview() {
    MainTheme {
        HorizontalMemberItem(
            model = UserModel(
                id = 1,
                domain = "Sample domain",
                firstName = "It's",
                lastName = "Sample",
                isSelected = true
            ),
            onRemoveClick = {}
        )
    }
}

@ExperimentalCoilApi
@Preview
@Composable
fun DarkHorizontalMemberItem_Preview() {
    MainTheme(darkThemeEnabled = true) {
        HorizontalMemberItem(
            model = UserModel(
                id = 1,
                domain = "Sample domain",
                firstName = "It's",
                lastName = "Sample",
                isSelected = true
            ),
            onRemoveClick = {}
        )
    }
}