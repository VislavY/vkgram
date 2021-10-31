package ru.vyapps.vkgram.new_conversation.screens.conversation_creation.views.gallery

import android.content.Intent
import android.net.Uri
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme

@Composable
fun PermDeniedAlertDialog(
    visibleState: MutableState<Boolean>,
    text: String
) {
    if (!visibleState.value) return

    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = {
            visibleState.value = false
        },
        title = {
            Text(
                text = "VKgram",
                color = VKgramTheme.palette.primaryText,
                style = VKgramTheme.typography.title
            )
        },
        text = {
            Text(
                text = text,
                color = VKgramTheme.palette.primaryText,
                style = VKgramTheme.typography.body1

            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val intent =
                        Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    context.startActivity(intent)

                    visibleState.value = false
                }
            ) {
                Text(
                    text = "Разрешить".uppercase(),
                    color = VKgramTheme.palette.secondary,
                    style = VKgramTheme.typography.button
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    visibleState.value = false
                }
            ) {
                Text(
                    text = "Отклонить".uppercase(),
                    color = VKgramTheme.palette.secondary,
                    style = VKgramTheme.typography.button
                )
            }
        }
    )
}

@Preview
@Composable
fun PermDeniedAlertDialog_Preview() {
    MainTheme {
        PermDeniedAlertDialog(
            visibleState = remember { mutableStateOf(true) },
            text = "Sample text"
        )
    }
}

@Preview
@Composable
fun DarkPermDeniedAlertDialog_Preview() {
    MainTheme(darkThemeEnabled = true) {
        PermDeniedAlertDialog(
            visibleState = remember { mutableStateOf(true) },
            text = "Sample text"
        )
    }
}