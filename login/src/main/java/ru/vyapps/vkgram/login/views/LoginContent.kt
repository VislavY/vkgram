package ru.vyapps.vkgram.login.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme
import ru.vyapps.vkgram.login.R

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF01a9f4),
                        Color(0xFF81d4fa)
                    )
                )
            )
            .fillMaxSize(),
        color = Color.Transparent
    ) {
        Card(
            modifier = Modifier
                .padding(24.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    modifier = Modifier.align(CenterHorizontally),
                    text = stringResource(R.string.login_hello),
                    color = VKgramTheme.palette.primaryText,
                    style = VKgramTheme.typography.topBarTitle
                )

                Text(
                    text = stringResource(R.string.login_thank_you),
                    color = VKgramTheme.palette.primaryText,
                    style = VKgramTheme.typography.body1
                )

                Spacer(Modifier.height(32.dp))

                Button(
                    modifier = Modifier.align(CenterHorizontally),
                    onClick = onLoginClick,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = VKgramTheme.palette.secondary),
                    contentPadding = PaddingValues(horizontal = 32.dp)
                ) {
                    Text(
                        text = stringResource(R.string.login_button_text),
                        color = Color.White,
                        style = VKgramTheme.typography.button
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginContent_Preview() {
    MainTheme {
        LoginContent(onLoginClick = { })
    }
}

@Preview
@Composable
fun DarkLoginContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        LoginContent(onLoginClick = { })
    }
}