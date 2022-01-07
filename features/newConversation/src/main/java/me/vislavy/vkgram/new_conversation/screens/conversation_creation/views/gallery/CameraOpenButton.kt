package me.vislavy.vkgram.new_conversation.screens.conversation_creation.views.gallery

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@ExperimentalPermissionsApi
@Composable
fun CameraOpenButton() {
    val cameraPermissionState =
        rememberPermissionState(Manifest.permission.CAMERA)
    val permDeniedAlertDialogState = remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .clickable {
                if (!cameraPermissionState.hasPermission) {
                    if (cameraPermissionState.shouldShowRationale
                        || !cameraPermissionState.permissionRequested
                    ) {
                        cameraPermissionState.launchPermissionRequest()
                    } else {
                        permDeniedAlertDialogState.value = true
                    }
                }

            }
            .size(128.dp),
        shape = RoundedCornerShape(topStart = 8.dp),
        color = VKgramTheme.palette.secondary
    ) {
        PermDeniedAlertDialog(
            visibleState = permDeniedAlertDialogState,
            text = "Разрешите доступ к камере, чтобы делать фото и записывать видео."
        )

        Icon(
            imageVector = Icons.Rounded.PhotoCamera,
            contentDescription = null,
            modifier = Modifier.padding(42.dp),
            tint = Color.White
        )
    }
}

@ExperimentalPermissionsApi
@Preview
@Composable
fun CameraOpenButton_Preview() {
    MainTheme {
        CameraOpenButton()
    }
}

@ExperimentalPermissionsApi
@Preview
@Composable
fun DarkCameraOpenButton_Preview() {
    MainTheme(darkThemeEnabled = true) {
        CameraOpenButton()
    }
}