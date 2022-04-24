package me.vislavy.vkgram.app_settings.views

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import me.vislavy.vkgram.app_settings.R
import me.vislavy.vkgram.core.datastore.LocalSettingsDataStore
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun AppSettingsTopBar(
    modifier: Modifier = Modifier,
    color: Color = VKgramTheme.palette.primary,
    navController: NavController = rememberNavController()
) {
    val settingsDataStore = LocalSettingsDataStore.current
    val coroutineScope = rememberCoroutineScope()

    TopAppBar(
        modifier = modifier,
        backgroundColor = color
    ) {
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = VKgramTheme.palette.onPrimary
            )
        }

        Spacer(Modifier.width(16.dp))

        Text(
            text = stringResource(R.string.app_settings),
            color = VKgramTheme.palette.onSurface,
            style = VKgramTheme.typography.topBarTitle
        )

        Spacer(Modifier.weight(1F))


        val darkThemeEnabledState = settingsDataStore.getDarkThemeEnabled().collectAsState(false)
        Box {
            Row {
                AnimatedVisibility(
                    visible = !darkThemeEnabledState.value,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            settingsDataStore.setDarkThemeEnabled(true)
                        }
                    }) {
                        Icon(
                            modifier = Modifier.rotate(260F),
                            imageVector = Icons.Default.DarkMode,
                            contentDescription = null,
                            tint = VKgramTheme.palette.onPrimary
                        )
                    }
                }
            }

            Row {
                AnimatedVisibility(
                    visible = darkThemeEnabledState.value,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            settingsDataStore.setDarkThemeEnabled(false)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.LightMode,
                            contentDescription = null,
                            tint = VKgramTheme.palette.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSettingTopBar() {
    MainTheme {
        AppSettingsTopBar()
    }
}