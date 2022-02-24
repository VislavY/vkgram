package me.vislavy.vkgram.message_history.views.gallery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramColor
import me.vislavy.vkgram.core.theme.VKgramTheme
import java.io.File

@ExperimentalAnimationApi
@Composable
fun ImageItem(
    modifier: Modifier = Modifier,
    onClick: (File) -> Unit,
    onSelectClick: (File) -> Unit,
    file: File? = null,
    isSelected: Boolean = false
) {
    Surface(
        modifier
            .size(100.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = {
                    onClick(file!!)
                }
            )
    ) {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(VKgramTheme.palette.onSurface.copy(0.12F)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(42.dp),
                    imageVector = Icons.Outlined.Photo,
                    contentDescription = null,
                    tint = VKgramTheme.palette.onSurface
                )
            }

            val imageScaleAnim by animateFloatAsState(
                targetValue = if (isSelected) 0.80F else 1F,
                animationSpec = tween(
                    durationMillis = 150,
                    easing = LinearEasing
                )
            )
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .scale(imageScaleAnim),
                painter = rememberImagePainter(
                    data = file,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false),
                            onClick = {
                                onSelectClick(file!!)
                            }
                        )
                        .background(
                            color = VKgramTheme.palette.primaryIndicator.copy(0.12F),
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = VKgramColor.White,
                            shape = CircleShape
                        )
                ) {
                    AnimatedVisibility(
                        visible = isSelected,
                        enter = scaleIn(tween(200)),
                        exit = scaleOut(tween(200))
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = VKgramTheme.palette.primaryIndicator,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.padding(4.dp),
                                imageVector = Icons.Rounded.Done,
                                contentDescription = null,
                                tint = VKgramTheme.palette.onSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun PreviewImageItem() {
    MainTheme {
        ImageItem(
            onSelectClick = { },
            onClick = { }
        )
    }
}