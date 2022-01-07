package me.vislavy.vkgram.home.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.api.data.User
import me.vislavy.vkgram.home.R

@Composable
fun NavigationDialog(
    isOpenedState: MutableState<Boolean> = remember { mutableStateOf(true) },
    profileModel: User,
    navController: NavController = rememberNavController()
) {
    if (isOpenedState.value) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {
                isOpenedState.value = false
            },
            shape = RoundedCornerShape(16.dp),
            buttons = {
                IconButton(
                    onClick = {
                        isOpenedState.value = false
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onPrimary
                    )
                }

                Row(
                    modifier = Modifier
                        .clickable(onClick = {
                            navController.navigate("${Destinations.Profile}/${profileModel.id}")
                            isOpenedState.value = false
                        })
                        .padding(horizontal = 16.dp),
                    verticalAlignment = CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(48.dp),
                        painter = rememberImagePainter(
                            data = profileModel.photo,
                            builder = {
                                crossfade(true)
                                placeholder(R.drawable.photo_placeholder_56)
                                transformations(CircleCropTransformation())
                            }
                        ),
                        contentDescription = null
                    )

                    Spacer(Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "${profileModel.firstName} ${profileModel.lastName}",
                            color = VKgramTheme.palette.primaryText,
                            style = VKgramTheme.typography.subtitle2
                        )

                        Text(
                            text = profileModel.domain,
                            color = VKgramTheme.palette.secondaryText,
                            style = VKgramTheme.typography.body2
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Divider(
                    color = VKgramTheme.palette.surface,
                    thickness = 2.dp
                )

                NavigationItemButton(
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = VKgramTheme.palette.onPrimary
                        )
                    }
                ) {
                    Text(
                        text = "Profile settings",
                        color = VKgramTheme.palette.primaryText,
                        style = VKgramTheme.typography.button
                    )
                }

                NavigationItemButton(
                    onClick = {
                        navController.navigate(Destinations.AppSettings)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = VKgramTheme.palette.onPrimary
                        )
                    }
                ) {
                    Text(
                        text = "App settings",
                        color = VKgramTheme.palette.primaryText,
                        style = VKgramTheme.typography.button
                    )
                }

                Divider(
                    color = VKgramTheme.palette.surface,
                    thickness = 2.dp
                )

                Box(Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier
                            .align(Center)
                            .padding(vertical = 8.dp),
                        text = "VKgram version 1.0.0",
                        color = VKgramTheme.palette.secondaryText,
                        style = VKgramTheme.typography.body2
                    )
                }
            }
        )
    }
}