package me.vislavy.vkgram.profile.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.profile.models.ProfileViewState
import me.vislavy.vkgram.api.data.User
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ProfileTopBar(
    modifier: Modifier = Modifier,
    viewState: ProfileViewState.Display,
    navController: NavController = rememberNavController()
) {
    Box(modifier.height(250.dp)) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = rememberImagePainter(
                data = viewState.user.photoOrig
            ),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(
                color = Color.Black.copy(0.4F),
                blendMode = BlendMode.Darken)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(0.6F))
                    )
                )
                .fillMaxWidth()
                .height(20.dp)
        )

        IconButton(
            modifier = Modifier.padding(top = 32.dp),
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
        ) {
            Text(
                text = "${viewState.user.firstName} ${viewState.user.lastName}",
                color = Color.White,
                style = VKgramTheme.typography.title
            )

            val subtitle = if (viewState.user.online) {
                "онлайн"
            } else {
                val currentDate = Calendar.getInstance()
                val currentDay = currentDate.get(Calendar.DAY_OF_MONTH)
                val currentMount = currentDate.get(Calendar.MONTH)
                val currentYear = currentDate.get(Calendar.YEAR)

                val lastActivityDate = Calendar.getInstance()
                lastActivityDate.time = viewState.user.lastSeen!!.time
                val lastActivityDay = lastActivityDate.get(Calendar.DAY_OF_MONTH)
                val lastActivityMount = lastActivityDate.get(Calendar.MONTH)
                val lastActivityYear = lastActivityDate.get(Calendar.YEAR)

                val dateFormat = SimpleDateFormat("k:mm", Locale.getDefault())
                if (
                    currentDay == lastActivityDay
                    && currentMount == lastActivityMount
                    && currentYear == lastActivityYear
                ) {
                    "был(а) в ${dateFormat.format(lastActivityDate.time)}"
                } else {
                    val dayOfMouthDateFormat = SimpleDateFormat("d MMM", Locale.getDefault())
                    "был(а) ${dayOfMouthDateFormat.format(lastActivityDate.time)} в ${dateFormat.format(lastActivityDate.time)}"
                }
            }
            Text(
                text = subtitle,
                color = Color.White,
                style = VKgramTheme.typography.body2
            )
        }
    }
}

@Preview
@Composable
fun ProfileTopBar_Preview() {
    MainTheme {
        ProfileTopBar(
            viewState = ProfileViewState.Display(
                user = User(
                    id = 1,
                    domain = "Sample domain",
                    firstName = "It's",
                    lastName = "Sample"
                )
            )
        )
    }
}

@Preview
@Composable
fun DarkProfileTopBar_Preview() {
    MainTheme {
        ProfileTopBar(
            viewState = ProfileViewState.Display(
                user = User(
                    id = 1,
                    domain = "Sample domain",
                    firstName = "It's",
                    lastName = "Sample"
                )
            )
        )
    }
}