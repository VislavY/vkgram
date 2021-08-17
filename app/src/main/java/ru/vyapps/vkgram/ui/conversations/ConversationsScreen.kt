package ru.vyapps.vkgram.ui.conversations

import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.vyapps.vkgram.ui.theme.VKgramTheme

@Composable
fun ConversationsScreen() {
    Scaffold(bottomBar = {
        BottomAppBar() {

        }
    }) {

    }
}

@Preview(showBackground = true)
@Composable
fun ConversationsScreenPreview() {
    VKgramTheme {
        ConversationsScreen()
    }
}