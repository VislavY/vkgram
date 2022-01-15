package me.vislavy.vkgram.search.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import me.vislavy.vkgram.api.data.ConversationType
import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.views.ItemButton
import me.vislavy.vkgram.search.R

@Composable
fun FoundConversation(
    modifier: Modifier = Modifier,
    model: ConversationModel,
    onClick: (ConversationModel) -> Unit
) {
    ItemButton(onClick = {
        onClick(model)
    }) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            color = VKgramTheme.palette.background
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = rememberImagePainter(
                        data = model.photo,
                        builder = {
                            placeholder(R.drawable.photo_placeholder_56)
                            crossfade(true)
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = null
                )

                Spacer(Modifier.width(16.dp))

                Column {
                    Text(
                        text = model.title,
                        color = VKgramTheme.palette.primaryText,
                        style = VKgramTheme.typography.subtitle2
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = when (model.properties.type) {
                           ConversationType.Chat -> when {
                                (model.memberCount == 1) -> "${model.memberCount} участник"
                                (model.memberCount < 5) -> "${model.memberCount} участника"
                                else -> "${model.memberCount} участников"
                            }
                            ConversationType.Group -> "группа"
                            ConversationType.User -> "пользователь"
                        },
                        color = VKgramTheme.palette.secondaryText,
                        style = VKgramTheme.typography.body2
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewFoundConversation() {
    MainTheme {
        FoundConversation(
            model = ConversationModel(
                title = "Sample title"
            ),
            onClick = { }
        )
    }
}