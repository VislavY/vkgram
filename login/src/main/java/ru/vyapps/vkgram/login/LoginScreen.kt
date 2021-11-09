package ru.vyapps.vkgram.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vk.api.sdk.VK
import kotlinx.coroutines.delay
import ru.vyapps.vkgram.core.Destinations
import ru.vyapps.vkgram.core.theme.VKgramTheme

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun LoginScreen(
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        while (true) {
            if (VK.isLoggedIn()) {
                navController.navigate(Destinations.Home)
            }

            delay(1000)
        }
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
                vertical = 8.dp,
            ),
            style = VKgramTheme.typography.topBarTitle
        )

        Text(   
            text = stringResource(R.string.login_thanks_body),
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 8.dp
            ),
            style = VKgramTheme.typography.body1
        )
    }

    val activity = (LocalContext.current as Activity)
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    Box(
        modifier = Modifier.fillMaxHeight(),
        contentAlignment = Alignment.BottomCenter) {
        Column {
            Button(
                onClick = {
                    viewModel.login(activity)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = CircleShape,
            ) {
                Text(
                    text = stringResource(R.string.login_button_text),
                )
            }

            Spacer(Modifier.height(52.dp))
        }
    }
}