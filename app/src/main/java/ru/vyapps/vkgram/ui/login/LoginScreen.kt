package ru.vyapps.vkgram.ui.login

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.vyapps.vkgram.R
import ru.vyapps.vkgram.ui.theme.Cyan500
import ru.vyapps.vkgram.ui.theme.Typography

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel()
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Cyan500)
    }

    Column {
        Image(
            painter = painterResource(R.drawable.login_header),
            contentDescription = stringResource(R.string.login_header_content_description),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.login_hello_headline),
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 8.dp
            ),
            style = Typography.h4
        )

        Text(
            text = stringResource(R.string.login_thanks_body),
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 8.dp
            ),
        )
    }

    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxHeight(),
        contentAlignment = Alignment.BottomCenter) {
        Column {
            Button(
                onClick = { viewModel.login(context as Activity) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = CircleShape
            ) {
                Text(
                    text = stringResource(R.string.login_button_text),
                )
            }

            Spacer(Modifier.height(52.dp))
        }
    }


}