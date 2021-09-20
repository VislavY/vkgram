package ru.vyapps.vkgram.ui.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vk.api.sdk.VK
import kotlinx.coroutines.delay
import ru.vyapps.vkgram.R
import ru.vyapps.vkgram.ui.Destinations
import ru.vyapps.vkgram.ui.theme.Cyan500
import ru.vyapps.vkgram.ui.theme.Typography

@Composable
fun LoginScreen(
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = viewModel()
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Cyan500)
    }

    LaunchedEffect(Unit) {
        while (true) {
            if (VK.isLoggedIn()) {
                navController.navigate(Destinations.CONVERSATION_LIST_SCREEN)
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

    val activity = (LocalContext.current as Activity)
    Box(
        modifier = Modifier.fillMaxHeight(),
        contentAlignment = Alignment.BottomCenter) {
        Column {
            Button(
                onClick = { viewModel.login(activity) },
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