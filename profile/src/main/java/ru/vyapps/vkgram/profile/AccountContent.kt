package ru.vyapps.vkgram.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme

@Composable
fun AccountContent() {
    Column(Modifier.fillMaxSize()) {
        Text(
            text = "Аккаунт",
            modifier = Modifier.padding(horizontal = 16.dp),
            color = VKgramTheme.palette.secondary,
            style = VKgramTheme.typography.subtitle1
        )
    }
}

@Preview
@Composable
fun AccountContent_Preview() {
    MainTheme {
        AccountContent()
    }
}

@Preview
@Composable
fun DarkAccountContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        AccountContent()
    }
}