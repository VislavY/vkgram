package me.vislavy.vkgram.message_history.views

import android.graphics.drawable.Drawable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.Coil
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import me.vislavy.vkgram.api.data.Photo
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramColor
import kotlin.math.abs

@Composable
fun ImageContent(
    modifier: Modifier = Modifier,
    model: Photo,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val conf = LocalConfiguration.current

    var imageDrawable by remember { mutableStateOf<Drawable?>(null) }

    LaunchedEffect(Unit) {
        val imageProperties = model.sizes.last()
        val imageRequest = ImageRequest.Builder(context)
            .data(imageProperties.url)
            .allowHardware(false)
            .build()
        imageDrawable = Coil.execute(imageRequest).drawable
    }
    val imagePainter = rememberImagePainter(imageDrawable)

    var surfaceColor by remember { mutableStateOf(VKgramColor.Black) }
    imageDrawable?.let { it ->
        Palette.from(it.toBitmap()).generate() { palette ->
            val primaryColor = palette?.dominantSwatch?.rgb ?: return@generate
            surfaceColor = Color(color = primaryColor)
        }
    }

    var isUiVisible by remember { mutableStateOf(true) }
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false,
            isNavigationBarContrastEnforced = false
        )
    }

    var isTapScale by remember { mutableStateOf(false) }

    var imageScale by remember { mutableStateOf(1F) }
    val animateImageScale by animateFloatAsState(imageScale)
    var imageOffset by remember { mutableStateOf(Offset.Zero) }
    val animateImageOffset by animateOffsetAsState(imageOffset)
    val transformableState = rememberTransformableState { zoomChange, _, _ ->
        imageScale *= zoomChange
    }
    if (!transformableState.isTransformInProgress) {
        imageScale = when {
            imageScale < 1F -> 1F
            imageScale > 3F -> 3F
            else -> imageScale
        }

        if (imageScale == 1F) {
            isTapScale = false
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .transformable(transformableState)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consumeAllChanges()
                        if (imageScale == 1F) {
                            imageOffset = imageOffset.copy(y = imageOffset.y + dragAmount.y)
                        } else {
//                            val localOffset = Offset(
//                                x = (imageOffset.x + dragAmount.x) / conf.screenWidthDp / (imageScale - 1F),
//                                y = ((imageOffset.y + dragAmount.y) / conf.screenHeightDp / (imageScale - 1F)) * 4
//                            )
//                            if (localOffset.x <= 1F && localOffset.x >= -1F) {
//                            }

                            imageOffset += dragAmount
                        }
                    },
                    onDragEnd = {
                        if (imageScale == 1F) {
                            if (abs(imageOffset.y) > conf.screenHeightDp / 2) onDismiss()

                            imageOffset = Offset.Zero
                        }
                    }
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        isUiVisible = !isUiVisible
                    },
                    onDoubleTap = { offset ->
                        val localOffset = Offset(
                            x = conf.screenWidthDp - offset.x,
                            y = conf.screenHeightDp - offset.y
                        )
                        if (!isTapScale) {
                            imageOffset += localOffset
                            imageScale += 1F
                        } else {
                            imageOffset -= localOffset
                            imageScale = 1F
                        }

                        isTapScale = !isTapScale
                    }
                )
            },
        color = surfaceColor
    ) {
        Box {
            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .zIndex(1F),
                visible = isUiVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
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
            }

            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = animateImageScale,
                        scaleY = animateImageScale,
                        translationX = animateImageOffset.x,
                        translationY = animateImageOffset.y
                    ),
                painter = imagePainter,
                contentDescription = null
            )

            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .zIndex(1F),
                visible = isUiVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
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
}

@Preview
@Composable
fun PreviewImageContent() {
    MainTheme {
        ImageContent(
            model = Photo(),
            onDismiss = { }
        )
    }
}