package me.vislavy.vkgram.new_conversation.screens.conversation_creation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun ConversationNameTextField(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit
) {
    val interactionSource = MutableInteractionSource()
    val isFocusedState = interactionSource.collectIsFocusedAsState()
    val conversationNameState = remember { mutableStateOf("") }
    BasicTextField(
        value = conversationNameState.value,
        onValueChange = { enteredText ->
            conversationNameState.value = enteredText
            onTextChange(conversationNameState.value)
        },
        modifier = modifier.background(VKgramTheme.palette.background),
        textStyle = VKgramTheme.typography.body1.copy(VKgramTheme.palette.onSurface),
        singleLine = true,
        interactionSource = interactionSource
    ) {
        Column {
            Text(
                text = "Название беседы",
                color = if (conversationNameState.value.isEmpty()) {
                    VKgramTheme.palette.onSurface
                } else {
                    VKgramTheme.palette.background
                },
                style = VKgramTheme.typography.body1
            )

            Spacer(Modifier.height(8.dp))

            Divider(
                color = if (isFocusedState.value) {
                    VKgramTheme.palette.primary
                } else {
                    VKgramTheme.palette.onSurface
                }
            )
        }

        it()
    }
}


@Preview
@Composable
fun ConversationNameTextField_Preview() {
    MainTheme {
        ConversationNameTextField(onTextChange = { })
    }
}

@Preview
@Composable
fun DarkConversationNameTextField_Preview() {
    MainTheme(darkThemeEnabled = true) {
        ConversationNameTextField(onTextChange = { })
    }
}
