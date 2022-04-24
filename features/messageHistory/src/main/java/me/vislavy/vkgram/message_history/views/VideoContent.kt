package me.vislavy.vkgram.message_history.views

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.Coil
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import me.vislavy.vkgram.api.data.Video
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramColor

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoContent(
    modifier: Modifier = Modifier,
    model: Video,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    var imageDrawable by remember { mutableStateOf<Drawable?>(null) }

    LaunchedEffect(Unit) {
        val imageRequest = ImageRequest.Builder(context)
            .data(model.image.first().url)
            .allowHardware(false)
            .build()
//        imageDrawable = Coil.execute(imageRequest).drawable
    }

    var surfaceColor by remember { mutableStateOf(VKgramColor.Black) }
    imageDrawable?.let { it ->
        Palette.from(it.toBitmap()).generate() { palette ->
            val primaryColor = palette?.dominantSwatch?.rgb ?: return@generate
            surfaceColor = Color(color = primaryColor)
        }
    }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false,
            isNavigationBarContrastEnforced = false
        )
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = surfaceColor
    ) {
        Box {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .zIndex(1F)
                    .background(VKgramColor.Black.copy(0.4F))
                    .statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = VKgramColor.White
                        )
                    }

                    Spacer(Modifier.weight(1F))

                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = null,
                            tint = VKgramColor.White
                        )
                    }
                }
            }

            AndroidView(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = WebViewClient()
                        loadUrl(model.playerUrl)
                    }
                }
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .zIndex(1F)
                    .background(VKgramColor.Black.copy(0.4F))
                    .navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FavoriteBorder,
                            contentDescription = null,
                            tint = VKgramColor.White
                        )
                    }

                    IconButton(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ChatBubbleOutline,
                            contentDescription = null,
                            tint = VKgramColor.White
                        )
                    }


                    IconButton(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = null,
                            tint = VKgramColor.White
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewVideoContent() {
    MainTheme {
        VideoContent(
            model = Video(),
            onDismiss = { }
        )
    }
}