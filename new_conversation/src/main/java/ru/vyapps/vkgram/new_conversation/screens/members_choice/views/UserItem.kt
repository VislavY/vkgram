package ru.vyapps.vkgram.new_conversation.screens.members_choice.views

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import ru.vyapps.vkgram.core.theme.*
import ru.vyapps.vkgram.core.views.ItemButton
import ru.vyapps.vkgram.new_conversation.R
import ru.vyapps.vkgram.new_conversation.UserModel

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun UserItem(
    model: UserModel,
    onClick: (UserModel) -> Unit
) {
    ItemButton(onClick = {
        model.isSelected = !model.isSelected
        onClick(model)
    }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
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

                Row(Modifier.align(Alignment.BottomEnd)) {
                    AnimatedVisibility(
                        visible = model.isSelected,
                        enter = scaleIn(tween(200)),
                        exit = scaleOut(tween(200))
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = VKgramTheme.palette.background,
                                    shape = CircleShape
                                )
                                .size(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = VKgramTheme.palette.secondary,
                                        shape = CircleShape
                                    )
                                    .size(12.dp)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = "${model.firstName} ${model.lastName}",
                    color = VKgramTheme.palette.primaryText,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = VKgramTheme.typography.body1
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = model.domain,
                    color = VKgramTheme.palette.secondaryText,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = VKgramTheme.typography.body2
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Preview
@Composable
fun UserItem_Preview() {
    MainTheme() {
        UserItem(
            model = UserModel(
                id = 1,
                domain = "Sample domain",
                firstName = "It's",
                lastName = "Sample",
                isSelected = true
            ),
            onClick = { }
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Preview
@Composable
fun DarkUserItem_Preview() {
    MainTheme(darkThemeEnabled = true) {
        UserItem(
            model = UserModel(
                id = 1,
                domain = "Sample domain",
                firstName = "It's",
                lastName = "Sample",
                isSelected = true
            ),
            onClick = { }
        )
    }
}