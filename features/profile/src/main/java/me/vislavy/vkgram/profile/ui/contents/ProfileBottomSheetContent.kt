package me.vislavy.vkgram.profile.ui.contents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.api.local.entities.StoredUser
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun ProfileBottomSheetContent(
    modifier: Modifier = Modifier,
    model: StoredUser
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.surface
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .width(16.dp)
                    .height(4.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = VKgramTheme.palette.surfaceVariant,
                        shape = CircleShape
                    )
            )

            Spacer(Modifier.height(16.dp))

            model.status?.let {
                UserProfileInfo(
                    bodyColor = VKgramTheme.palette.onSurface,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.SentimentVerySatisfied,
                            contentDescription = null
                        )
                    },
                    body = {
                        Text(
                            text = it,
                            style = VKgramTheme.typography.bodyMedium
                        )
                    }
                )
            }
        }
    }
}