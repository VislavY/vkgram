package me.vislavy.vkgram.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.R
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.theme.defaults.VKgramButtonDefaults

@Composable
fun ErrorContent(
    modifier: Modifier = Modifier,
    onReload: () -> Unit = { }
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.surface
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.width(250.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.error_message),
                    textAlign = TextAlign.Center,
                    color = VKgramTheme.palette.onSurface,
                    style = VKgramTheme.typography.bodyLarge,
                )

                Spacer(Modifier.height(16.dp))

                TextButton(
                    onClick = onReload,
                    colors = VKgramButtonDefaults.textButtonColors()
                ) {
                    Text(
                        text = stringResource(R.string.error_reload),
                        style = VKgramTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Preview("Error content")
@Composable
fun PreviewErrorContent() {
    MainTheme {
        ErrorContent()
    }
}

@Preview("Error content in dark theme")
@Composable
fun PreviewErrorContent_Dark() {
    MainTheme(darkThemeEnabled = true) {
        ErrorContent()
    }
}