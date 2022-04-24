package me.vislavy.vkgram.login.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.theme.defaults.VKgramButtonDefaults
import me.vislavy.vkgram.login.R

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.primary
    ) {
        Card(
            modifier = Modifier
                .padding(24.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp)

        ) {
            Column(Modifier.padding(24.dp)) {
                Text(
                    text = stringResource(R.string.login_hello),
                    color = VKgramTheme.palette.onSurface,
                    style = VKgramTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.login_thank_you),
                    color = VKgramTheme.palette.onSurfaceVariant,
                    style = VKgramTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(32.dp))

                Button(
                    modifier = Modifier.align(CenterHorizontally),
                    colors = VKgramButtonDefaults.secondaryButtonColors(),
                    onClick = onLoginClick
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        style = VKgramTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}