package ru.vyapps.vkgram.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import ru.vyapps.vkgram.core.theme.*

@ExperimentalPagerApi
@ExperimentalCoilApi
@Composable
fun ProfileScreen(
    navController: NavController = rememberNavController(),
    viewModel: ProfileViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            ProfileTopBar(navController)
        }
    ) { padding ->
        val modifier = Modifier.padding(padding)
        ProfileContent(modifier.padding(top = 16.dp), viewModel)
    }
}

@Composable
fun ProfileTopBar(
    navController: NavController = rememberNavController()
) {
    TopAppBar(backgroundColor = Color.White, elevation = 0.dp) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(id = R.drawable.outline_close_24),
                contentDescription = null,
                tint = BlueGrey700
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun ProfilePager() {
    val pagerState = rememberPagerState()
    Row {
        for (id in 0..1) {
            val pageIsActive = (pagerState.currentPage == id)
            Box(
                modifier = Modifier
                    .background(
                        color = if (pageIsActive) LightBlue500 else BlueGrey50,
                        shape = CircleShape
                    )
                    .size(12.dp)
            )

            Spacer(Modifier.width(8.dp))
        }
    }

    HorizontalPager(2, state = pagerState) { page ->
        when (page) {
            0 -> AccountContent()
            1 -> SettingsContent()
        }
    }
}

@ExperimentalPagerApi
@ExperimentalCoilApi
@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel()
) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        val user = viewModel.user.collectAsState(null)
        with(user) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = rememberImagePainter(
                        data = value?.photo400OrigUrl,
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.photo_placeholder_200)
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )

                IconButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .background(LightBlue500, CircleShape)
                        .border(BorderStroke(4.dp, Color.White), CircleShape)
                        .size(56.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_add_a_photo_24),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "${value?.firstName}  ${value?.lastName}",
                color = BlueGrey900,
                style = Typography.h5
            )

            Spacer(Modifier.height(32.dp))

            ProfilePager()
        }
    }
}