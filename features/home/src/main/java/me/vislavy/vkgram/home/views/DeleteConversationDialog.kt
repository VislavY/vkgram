package me.vislavy.vkgram.home.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import me.vislavy.vkgram.api.data.ConversationType
import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.home.R

@Composable
fun DeleteConversationDialog(
    modifier: Modifier = Modifier,
    selectedConversations: List<ConversationModel> = emptyList(),
    color: Color = VKgramTheme.palette.surface,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    onDismiss: () -> Unit,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        backgroundColor = color,
        shape = shape,
        onDismissRequest = onDismiss,
        title = {
            if (selectedConversations.size == 1) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier.size(38.dp),
                        painter = rememberImagePainter(
                            data = selectedConversations[0].photo,
                            builder = {
                                crossfade(true)
                                transformations(CircleCropTransformation())
                                placeholder(R.drawable.photo_placeholder_56)
                            }
                        ),
                        contentDescription = null
                    )

                    Spacer(Modifier.width(16.dp))

                    Text(
                        text = if (selectedConversations[0].properties.type == ConversationType.Chat)
                            "Покинуть чат" else "Удалить беседу",
                        color = VKgramTheme.palette.onSurface,
                        style = VKgramTheme.typography.h6
                    )
                }
            } else {
                Text(
                    text = "Удалить ${selectedConversations.size} беседы",
                    color = VKgramTheme.palette.onSurface,
                    style = VKgramTheme.typography.h6
                )
            }
        },
        text = {
            if (selectedConversations.size == 1) {
                Column {
                    Text(
                        buildAnnotatedString {
                            append(
                                text = if (selectedConversations[0].properties.type == ConversationType.Chat)
                                    "Вы дейстительно хотите покинуть чат" else "Вы дейстительно хотите удалить беседу с "
                            )

                            withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                                append(selectedConversations[0].title)
                            }

                            append("?")
                        },
                        color = VKgramTheme.palette.onSurface,
                        style = VKgramTheme.typography.body1
                    )
                }
            } else {
                Column {
                    Text(
                        text = "Вы дейстительно хотите удалить выбранные беседы?",
                        color = VKgramTheme.palette.onSurface,
                        style = VKgramTheme.typography.body1
                    )
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelClick) {
                Text(
                    text = "Отмена".uppercase(),
                    color = VKgramTheme.palette.primary,
                    style = VKgramTheme.typography.button
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text(
                    text = (if (selectedConversations[0].properties.type == ConversationType.Chat)
                        "Покинуть" else "Удалить").uppercase(),
                    color = VKgramTheme.palette.error,
                    style = VKgramTheme.typography.button
                )
            }
        }
    )
}