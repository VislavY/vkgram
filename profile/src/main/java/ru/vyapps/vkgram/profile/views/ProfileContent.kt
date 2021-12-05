package ru.vyapps.vkgram.profile.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme
import ru.vyapps.vkgram.profile.models.ProfileViewState
import ru.vyapps.vkgram.vk_api.data.City
import ru.vyapps.vkgram.vk_api.data.User

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    viewState: ProfileViewState.Display,
    navController: NavController = rememberNavController()
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
//        val context = (LocalContext.current as Activity)
//        WindowCompat.setDecorFitsSystemWindows(context.window, false)
//        val systemUiController = rememberSystemUiController()
//        systemUiController.setStatusBarColor(Color.Black)


        Column {
            with (viewState.user) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Основное",
                    color = VKgramTheme.palette.secondary,
                    style = VKgramTheme.typography.subtitle2
                )

                ProfileInfoItem(
                    info = status,
                    infoType = "Статус"
                )

                ProfileInfoItem(
                    info = brightDate,
                    infoType = "День рождения"
                )

                city?.let {
                    ProfileInfoItem(
                        info = it.title,
                        infoType = "Город"
                    )
                }

                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = VKgramTheme.palette.surface
                )

                Spacer(Modifier.height(16.dp))

                var personalInformationIsExpanded by remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier.clickable(
                        onClick = {
                            personalInformationIsExpanded = !personalInformationIsExpanded
                        }
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Личное",
                        color = VKgramTheme.palette.secondary,
                        style = VKgramTheme.typography.subtitle2
                    )

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onSurface
                    )
                }

                if (personalInformationIsExpanded) {
                    if (about.isNotEmpty()) {
                        ProfileInfoItem(
                            info = about,
                            infoType = "О себе"
                        )
                    }

                    if (interests.isNotEmpty()) {
                        ProfileInfoItem(
                            info = interests,
                            infoType = "Интересы"
                        )
                    }

                    if (music.isNotEmpty()) {
                        ProfileInfoItem(
                            info = music,
                            infoType = "Любимая музыка"
                        )
                    }

                    if (films.isNotEmpty()) {
                        ProfileInfoItem(
                            info = films,
                            infoType = "Любимые фильмы"
                        )
                    }

                    if (books.isNotEmpty()) {
                        ProfileInfoItem(
                            info = books,
                            infoType = "Любимые книги"
                        )
                    }

                    if (games.isNotEmpty()) {
                        ProfileInfoItem(
                            info = games,
                            infoType = "Любимые игры"
                        )
                    }

                    if (quotes.isNotEmpty()) {
                        ProfileInfoItem(
                            info = quotes,
                            infoType = "Любимые цитаты"
                        )
                    }
                }

                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = VKgramTheme.palette.surface
                )

                career?.let {
                    Spacer(Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Карьера",
                        color = VKgramTheme.palette.secondary,
                        style = VKgramTheme.typography.subtitle2
                    )

                    if (it.company.isNotEmpty()) {
                        ProfileInfoItem(
                            info = it.company,
                            infoType = "Название компании"
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ProfileContent_Preview() {
    MainTheme {
        ProfileContent(
            viewState = ProfileViewState.Display(
                user = User(
                    id = 1,
                    domain = "Sample domain",
                    firstName = "It's",
                    lastName = "Sample",
                    status = "Sample status",
                    brightDate = "1.1.2047",
                    city = City(
                        id = 1,
                        title = "Moscow"
                    ),
                    about = "Android developer",
                    interests = "Programming, design, games",
                    music = "Punk/horror punk-rock & industrial metal",
                    films = "Doctor Who, Supernatural",
                    books = "Master and Margarita",
                    games = "Terraria, Starbound",
                    quotes = "The best revenge is a huge success"
                )
            )
        )
    }
}

@Composable
@Preview
fun DarkProfileContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        ProfileContent(
            viewState = ProfileViewState.Display(
                user = User(
                    id = 1,
                    domain = "Sample domain",
                    firstName = "It's",
                    lastName = "Sample",
                    status = "Sample status",
                    brightDate = "1.1.2047",
                    city = City(
                        id = 1,
                        title = "Moscow"
                    ),
                    about = "Android developer",
                    interests = "Programming, design, games",
                    music = "Punk/horror punk-rock & industrial metal",
                    films = "Doctor Who, Supernatural",
                    books = "Master and Margarita",
                    games = "Terraria, Starbound",
                    quotes = "The best revenge is a huge success"
                )
            )
        )
    }
}