package me.vislavy.vkgram.app_settings.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.app_settings.R
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.theme.VKgramTypography

@Composable
fun FontSizeDialogContent(
    onSelect: (VKgramTypography.FontSize) -> Unit,
    currentFontStyle: VKgramTypography.FontSize
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = VKgramTheme.palette.background,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(vertical =  16.dp)) {
            VKgramTypography.FontSize.values().forEach { fontSize ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onSelect(fontSize)
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple()
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(16.dp))

                    RadioButton(
                        selected = (currentFontStyle == fontSize),
                        onClick = {
                            onSelect(fontSize)
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = VKgramTheme.palette.secondary)
                    )

                    Text(
                        text = when (fontSize) {
                            VKgramTypography.FontSize.Small -> stringResource(R.string.app_settings_small)
                            VKgramTypography.FontSize.Normal -> stringResource(R.string.app_settings_normal)
                            VKgramTypography.FontSize.Medium -> stringResource(R.string.app_settings_medium)
                            VKgramTypography.FontSize.Big -> stringResource(R.string.app_settings_big)
                        },
                        color = VKgramTheme.palette.primaryText,
                        style = VKgramTheme.typography.body1
                    )

                    Spacer(Modifier.width(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewFontDialogContent() {
    MainTheme {
        FontSizeDialogContent(
            onSelect = { },
            currentFontStyle = VKgramTypography.FontSize.Normal
        )
    }
}