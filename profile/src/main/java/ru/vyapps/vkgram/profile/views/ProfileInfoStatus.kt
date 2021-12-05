package ru.vyapps.vkgram.profile.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme

@Composable
fun ProfileInfoItem(
    modifier: Modifier = Modifier,
    info: String,
    infoType: String
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = VKgramTheme.palette.background
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        ) {
            Text(
                text = info,
                color = VKgramTheme.palette.primaryText,
                style = VKgramTheme.typography.body1
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = infoType,
                color = VKgramTheme.palette.secondaryText,
                style = VKgramTheme.typography.body2
            )
        }
    }
}

@Preview
@Composable
fun ProfileInfoItem_Preview() {
    MainTheme {
        ProfileInfoItem(
            info = "Sample status",
            infoType = "Status"
        )
    }
}

@Preview
@Composable
fun DarkProfileInfoItem_Preview() {
    MainTheme(darkThemeEnabled = true) {
        ProfileInfoItem(
            info = "Sample status",
            infoType = "Status"
        )
    }
}