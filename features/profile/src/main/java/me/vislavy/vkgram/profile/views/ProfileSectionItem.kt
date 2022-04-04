package me.vislavy.vkgram.profile.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun ProfileSectionItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    leadingIcon: @Composable () -> Unit,
    text: String,
    trailingIcon: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = VKgramTheme.palette.onSurfaceVariant.copy(.8F)),
                onClick = onClick
            ),
        color = VKgramTheme.palette.surface,
        contentColor = VKgramTheme.palette.onSurfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon()

            Spacer(Modifier.width(16.dp))

            Text(
                modifier = Modifier.weight(1F),
                text = text,
                color = VKgramTheme.palette.onSurface,
                style = VKgramTheme.typography.bodyLarge
            )

            trailingIcon()
        }
    }
}

@Preview("Profile section item")
@Composable
fun PreviewProfileSectionItem() {
    MainTheme {
        ProfileSectionItem(
            onClick = { },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = null
                )
            },
            text = "Account",
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.ArrowRight,
                    contentDescription = null
                )
            }
        )
    }
}

@Preview("Profile section item dark theme")
@Composable
fun PreviewProfileSectionItem_Dark() {
    MainTheme(darkThemeEnabled = true) {
        ProfileSectionItem(
            onClick = { },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = null
                )
            },
            text = "Account",
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.ArrowRight,
                    contentDescription = null
                )
            }
        )
    }
}