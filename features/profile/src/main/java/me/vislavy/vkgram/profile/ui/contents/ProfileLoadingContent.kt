package me.vislavy.vkgram.profile.ui.contents

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material3.Divider
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.ext.vkgramPlaceholder
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun ProfileLoadingContent(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.surface
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            Divider(color = VKgramTheme.palette.surfaceVariant)

            Spacer(Modifier.height(8.dp))

            Row {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .vkgramPlaceholder()
                )

                Spacer(Modifier.width(16.dp))

                Column(
                    modifier = Modifier.height(80.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(22.dp)
                            .vkgramPlaceholder()
                    )

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(16.dp)
                            .vkgramPlaceholder()
                    )

                    Box(
                        modifier = Modifier
                            .width(140.dp)
                            .height(14.dp)
                            .vkgramPlaceholder()
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Row {
                repeat(2) { index ->
                    Box(
                        modifier = Modifier
                            .weight(1F)
                            .height(40.dp)
                            .vkgramPlaceholder()
                    )

                    if (index == 0) {
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            TabRowDefaults.Divider(color = VKgramTheme.palette.surfaceVariant)

            Spacer(Modifier.height(16.dp))

            repeat(2) { index ->
                Row {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .vkgramPlaceholder()
                    )

                    Spacer(Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(18.dp)
                            .vkgramPlaceholder()
                    )
                }

                if (index == 0) {
                    Spacer(Modifier.height(8.dp))
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .vkgramPlaceholder()
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            TabRowDefaults.Divider(color = VKgramTheme.palette.surfaceVariant)
        }
    }
}