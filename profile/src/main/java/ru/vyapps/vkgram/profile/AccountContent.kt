package ru.vyapps.vkgram.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vyapps.vkgram.core.theme.LightBlue500
import ru.vyapps.vkgram.core.theme.Typography

@Composable
fun AccountContent() {
    Column(Modifier.fillMaxSize()) {
        Text(
            text = "Аккаунт",
            modifier = Modifier.padding(horizontal = 16.dp),
            color = LightBlue500,
            style = Typography.subtitle1
        )
    }
}