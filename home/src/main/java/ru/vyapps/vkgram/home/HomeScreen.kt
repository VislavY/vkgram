package ru.vyapps.vkgram.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.core.Destinations
import ru.vyapps.vkgram.core.theme.*

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = viewModel()
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(Color.White)
    }

    val pagerState = rememberPagerState()
    Scaffold(
        topBar = {
            HomeTopBar(navController, viewModel)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(bottom = 16.dp),
                backgroundColor = LightBlue500,
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(
                        id = if (pagerState.currentPage == 0)
                            R.drawable.outline_sms_24 else R.drawable.outline_person_add_24
                    ),
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        val modifier = Modifier.padding(padding)
        HomeContent(
            modifier = modifier,
            pagerState = pagerState,
            navController = navController,
            viewModel = viewModel
        )
    }
}

@ExperimentalCoilApi
@Composable
fun HomeTopBar(
    navController: NavController = rememberNavController(),
    viewModel: HomeViewModel = viewModel()
) {
    TopAppBar(
        modifier = Modifier.padding(start = 16.dp),
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
        val user = viewModel.user.collectAsState(null)
        with (user) {
            IconButton(
                onClick = {
                    navController.navigate(Destinations.PROFILE_SCREEN)
                }
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = value?.photo200Url,
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.photo_placeholder_56)
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = BlueGrey700
                )
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun HomeTabRow(
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState()
) {
    val titles = listOf("Беседы", "Друзья")
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .background(BlueGrey50, RoundedCornerShape(8.dp))
            .fillMaxWidth()
    ) {
        Row {
            titles.forEachIndexed { index, title ->
                val isSelected = index == pagerState.currentPage
                Surface(
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    color = if (isSelected) Color.White else BlueGrey50,
                    elevation = if (isSelected) 8.dp else 0.dp,
                ) {
                    Tab(
                        selected = isSelected,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        content = {
                            Text(
                                text = title,
                                modifier = Modifier.padding(vertical = 4.dp),
                                color = if (isSelected) BlueGrey900 else BlueGrey300,
                                style = Typography.h6
                            )
                        }
                    )
                }
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalCoilApi
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(),
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = viewModel()
) {
    Column(modifier.padding(top = 16.dp)) {
        HomeTabRow(Modifier.padding(horizontal = 16.dp),  pagerState)

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalPager(2, state = pagerState) { page ->
            if (page == 0) {
                ConversationsContent(navController, viewModel)
            } else {
                FriendsContent()
            }
        }
    }
}