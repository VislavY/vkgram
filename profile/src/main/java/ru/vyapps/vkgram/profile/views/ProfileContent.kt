package ru.vyapps.vkgram.profile.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme
import ru.vyapps.vkgram.profile.R
import ru.vyapps.vkgram.profile.models.ProfileViewState
import ru.vyapps.vkgram.vk_api.data.City
import ru.vyapps.vkgram.vk_api.data.Relative
import ru.vyapps.vkgram.vk_api.data.RelativeType
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.padding(16.dp))

            with(viewState.user) {
                counters?.let {
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileCountCard(
                            count = it.friends,
                            subtitle = stringResource(R.string.friends)
                        )

                        Spacer(Modifier.width(16.dp))

                        ProfileCountCard(
                            count = it.commonFriends,
                            subtitle = stringResource(R.string.common_friends)
                        )

                        Spacer(Modifier.width(16.dp))

                        ProfileCountCard(
                            count = it.subscribes,
                            subtitle = stringResource(R.string.subscribes)
                        )
                    }

                    Spacer(Modifier.padding(16.dp))

                    Divider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = VKgramTheme.palette.surface
                    )

                    Spacer(Modifier.padding(16.dp))
                }

                ProfileInfoGroup(header = stringResource(R.string.basic_information)) {
                    ProfileInfoItem(
                        info = if (status.isNotBlank()) status else "-",
                        infoType = stringResource(R.string.status)
                    )

                    ProfileInfoItem(
                        info = birthDay,
                        infoType = stringResource(R.string.birthday)
                    )
                }

                city?.let {
                    ProfileInfoItem(
                        info = it.title,
                        infoType = stringResource(R.string.city)
                    )
                }

                ProfileInfoItem(
                    info = stringArrayResource(R.array.relation_variants)[relation],
                    infoType = stringResource(R.string.relation)
                )

                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = VKgramTheme.palette.surface
                )

                Spacer(Modifier.height(16.dp))

                ProfileInfoGroup(header = stringResource(R.string.basic_information)) {
                    if (homeTown.isNotBlank()) {
                        ProfileInfoItem(
                            info = homeTown,
                            infoType = stringResource(R.string.hometown)
                        )
                    }

                    personalInformation?.let {
                        if (it.languages.isEmpty()) return@let

                        ProfileInfoItem(
                            info = it.languages.joinToString(", "),
                            infoType = stringResource(R.string.languages)
                        )
                    }

                    val childes = emptyList<Relative>().toMutableList()
                    val siblings = emptyList<Relative>().toMutableList()
                    val parents = emptyList<Relative>().toMutableList()
                    val grandparents = emptyList<Relative>().toMutableList()
                    val grandchildes = emptyList<Relative>().toMutableList()
                    relatives.forEach { relative ->
                        when (relative.type) {
                            RelativeType.Child -> childes.add(relative)
                            RelativeType.Sibling -> siblings.add(relative)
                            RelativeType.Parent -> parents.add(relative)
                            RelativeType.Grandparent -> grandparents.add(relative)
                            RelativeType.Grandchild -> grandchildes.add(relative)
                        }
                    }

                    if (grandparents.isNotEmpty()) {
                        ProfileInfoItem(
                            info = grandparents.joinToString(", "),
                            infoType = stringResource(R.string.grandparents)
                        )
                    }

                    if (parents.isNotEmpty()) {
                        ProfileInfoItem(
                            info = parents.joinToString(", "),
                            infoType = stringResource(R.string.parents)
                        )
                    }

                    if (siblings.isNotEmpty()) {
                        ProfileInfoItem(
                            info = siblings.joinToString(", "),
                            infoType = stringResource(R.string.siblings)
                        )
                    }

                    if (childes.isNotEmpty()) {
                        ProfileInfoItem(
                            info = childes.joinToString(", "),
                            infoType = stringResource(R.string.childes)
                        )
                    }

                    if (grandchildes.isNotEmpty()) {
                        ProfileInfoItem(
                            info = grandchildes.joinToString(", "),
                            infoType = stringResource(R.string.grandchildes)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                personalInformation?.let {
                    ProfileInfoGroup(
                        header = stringResource(R.string.life_position)
                    ) {
                        ProfileInfoItem(
                            info = stringArrayResource(R.array.political_views_variants)[it.politicalViews],
                            infoType = stringResource(R.string.political_views)
                        )

                        ProfileInfoItem(
                            info = it.religion,
                            infoType = stringResource(R.string.world_view)
                        )

                        ProfileInfoItem(
                            info = stringArrayResource(R.array.personal_priority_variants)[it.mainInLife],
                            infoType = stringResource(R.string.main_in_life)
                        )

                        ProfileInfoItem(
                            info = stringArrayResource(R.array.main_in_peoples_variants)[it.mainInPeoples],
                            infoType = stringResource(R.string.main_in_peoples)
                        )

                        ProfileInfoItem(
                            info = stringArrayResource(R.array.views_variants)[it.viewsOnSmoking],
                            infoType = stringResource(R.string.views_on_smoking)
                        )

                        ProfileInfoItem(
                            info = stringArrayResource(R.array.views_variants)[it.viewsOnAlcohol],
                            infoType = stringResource(R.string.views_on_alcohol)
                        )

                        ProfileInfoItem(
                            info = it.inspiredBy,
                            infoType = stringResource(R.string.inspired_by)
                        )
                    }
                }
            }

//                var personalInformationIsExpanded by remember { mutableStateOf(false) }
//                Row(
//                    modifier = Modifier
//                        .clickable(
//                            onClick = {
//                                personalInformationIsExpanded = !personalInformationIsExpanded
//                            }
//                        )
//                        .fillMaxWidth()
//                        .padding(
//                            horizontal = 16.dp,
//                            vertical = 8.dp
//                        )
//                ) {
//                    Text(
//                        text = "Личное",
//                        color = VKgramTheme.palette.secondary,
//                        style = VKgramTheme.typography.subtitle2
//                    )
//
//                    Icon(
//                        imageVector = Icons.Default.KeyboardArrowUp,
//                        contentDescription = null,
//                        tint = VKgramTheme.palette.onSurface
//                    )
//                }
//
//                Column(Modifier.animateContentSize()) {
//                    if (personalInformationIsExpanded) {
//                        if (about.isNotEmpty()) {
//                            ProfileInfoItem(
//                                info = about,
//                                infoType = "О себе"
//                            )
//                        }
//
//                        if (interests.isNotEmpty()) {
//                            ProfileInfoItem(
//                                info = interests,
//                                infoType = "Интересы"
//                            )
//                        }
//
//                        if (music.isNotEmpty()) {
//                            ProfileInfoItem(
//                                info = music,
//                                infoType = "Любимая музыка"
//                            )
//                        }
//
//                        if (films.isNotEmpty()) {
//                            ProfileInfoItem(
//                                info = films,
//                                infoType = "Любимые фильмы"
//                            )
//                        }
//
//                        if (books.isNotEmpty()) {
//                            ProfileInfoItem(
//                                info = books,
//                                infoType = "Любимые книги"
//                            )
//                        }
//
//                        if (games.isNotEmpty()) {
//                            ProfileInfoItem(
//                                info = games,
//                                infoType = "Любимые игры"
//                            )
//                        }
//
//                        if (quotes.isNotEmpty()) {
//                            ProfileInfoItem(
//                                info = quotes,
//                                infoType = "Любимые цитаты"
//                            )
//                        }
//                    }
//                }


//                Divider(
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                    color = VKgramTheme.palette.surface
//                )

//                career?.let {
//                    Spacer(Modifier.height(16.dp))
//
//                    Text(
//                        modifier = Modifier.padding(horizontal = 16.dp),
//                        text = "Карьера",
//                        color = VKgramTheme.palette.secondary,
//                        style = VKgramTheme.typography.subtitle2
//                    )
//
//                    if (it.company.isNotEmpty()) {
//                        ProfileInfoItem(
//                            info = it.company,
//                            infoType = "Название компании"
//                        )
//                    }
//                }
        }
    }
}

@Composable
@Preview
fun PreviewProfileContent() {
    MainTheme {
        ProfileContent(
            viewState = ProfileViewState.Display(
                user = User(
                    id = 1,
                    domain = "Sample domain",
                    firstName = "It's",
                    lastName = "Sample",
                    status = "Sample status",
                    birthDay = "1.1.2047",
                    relation = 5,
                    city = City(
                        id = 1,
                        title = "Moscow"
                    ),
                    homeTown = "Moscow",
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
fun PreviewDarkProfileContent() {
    MainTheme(darkThemeEnabled = true) {
        ProfileContent(
            viewState = ProfileViewState.Display(
                user = User(
                    id = 1,
                    domain = "Sample domain",
                    firstName = "It's",
                    lastName = "Sample",
                    status = "Sample status",
                    birthDay = "1.1.2047",
                    relation = 5,
                    city = City(
                        id = 1,
                        title = "Moscow"
                    ),
                    homeTown = "Moscow",
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