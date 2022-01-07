package me.vislavy.vkgram.app_settings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.vislavy.vkgram.app_settings.R
import me.vislavy.vkgram.core.datastore.LocalSettingsDataStore
import me.vislavy.vkgram.core.datastore.LocalSettingsDataStoreProvider
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.theme.VKgramTypography

@Composable
fun AppSettingsContent(modifier: Modifier = Modifier) {
    val settingsDataStore = LocalSettingsDataStore.current
    val coroutineScope = rememberCoroutineScope()

    val fontSizeState =
        settingsDataStore.getFontSize().collectAsState(VKgramTypography.FontSize.Normal)
    var fontSizeDialogState by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
        Column {
            Spacer(Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.app_settings_appearance),
                color = VKgramTheme.palette.secondary,
                style = VKgramTheme.typography.subtitle2
            )

            SettingsButton(
                onClick = {

                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Palette,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onSurface
                    )
                },
                title = stringResource(R.string.app_settings_themes),
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(
                                color = VKgramTheme.palette.secondary,
                                shape = CircleShape
                            )
                    )
                }
            )

            SettingsButton(
                onClick = {
                    fontSizeDialogState = true
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.TextFields,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onSurface
                    )
                },
                title = stringResource(R.string.app_settings_font_size),
                subtitle = when (fontSizeState.value) {
                    VKgramTypography.FontSize.Small -> stringResource(R.string.app_settings_small)
                    VKgramTypography.FontSize.Normal -> stringResource(R.string.app_settings_normal)
                    VKgramTypography.FontSize.Medium -> stringResource(R.string.app_settings_medium)
                    VKgramTypography.FontSize.Big -> stringResource(R.string.app_settings_big)
                }
            )

            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = VKgramTheme.palette.surface
            )

            Spacer(Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.app_settings_invisible),
                color = VKgramTheme.palette.secondary,
                style = VKgramTheme.typography.subtitle2
            )

            val inputIndicatorEnabledState = settingsDataStore.getInputIndicatorEnabled().collectAsState(true)
            SettingsButton(
                onClick = {
                    coroutineScope.launch {
                        settingsDataStore.setInputIndicatorEnabled(!inputIndicatorEnabledState.value)
                    }
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onSurface
                    )
                },
                title = stringResource(R.string.app_settings_input_indicator),
                trailingIcon = {
                    Checkbox(
                        checked = inputIndicatorEnabledState.value,
                        onCheckedChange = { enabled ->
                            coroutineScope.launch {
                                settingsDataStore.setInputIndicatorEnabled(enabled)
                            }
                        },
                        colors = CheckboxDefaults.colors(checkedColor = VKgramTheme.palette.secondary)
                    )
                }
            )

            val readingMessagesEnabledState = settingsDataStore.getReadingMessagesEnabled().collectAsState(true)
            SettingsButton(
                onClick = {
                    coroutineScope.launch {
                        settingsDataStore.setReadingMessagesEnabled(!readingMessagesEnabledState.value)
                    }
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Sms,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onSurface
                    )
                },
                title = stringResource(R.string.app_settings_mark_messages_as_read),
                trailingIcon = {
                    Checkbox(
                        checked = readingMessagesEnabledState.value,
                        onCheckedChange = { enabled ->
                            coroutineScope.launch {
                                settingsDataStore.setReadingMessagesEnabled(enabled)
                            }
                        },
                        colors = CheckboxDefaults.colors(checkedColor = VKgramTheme.palette.secondary)
                    )
                }
            )
        }
    }

    if (fontSizeDialogState) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {
                fontSizeDialogState = false
            },
            backgroundColor = Color.Transparent,
            buttons = {
                FontSizeDialogContent(
                    currentFontStyle = fontSizeState.value,
                    onSelect = { fontSize ->
                        coroutineScope.launch {
                            settingsDataStore.setFontSize(fontSize)
                        }

                        fontSizeDialogState = false
                    }
                )
            }
        )
    }
}

@Preview
@Composable
fun PreviewAppSettingsContent() {
    LocalSettingsDataStoreProvider {
        MainTheme {
            AppSettingsContent()
        }
    }
}