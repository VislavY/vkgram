package ru.vyapps.vkgram.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import ru.vyapps.vkgram.core.theme.BlueGrey300
import ru.vyapps.vkgram.core.theme.BlueGrey900
import ru.vyapps.vkgram.core.theme.LightBlue500
import ru.vyapps.vkgram.core.theme.Typography
import ru.vyapps.vkgram.vk_api.data.Friend

@ExperimentalCoilApi
@Composable
fun FriendsContent(viewModel: HomeViewModel = viewModel()) {
    val friendsState = viewModel.friends.collectAsState()
    with(friendsState) {
        LazyColumn {
            itemsIndexed(value) { index, friend ->
                FriendItem(friend, Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

                if (index == (value.size - 5)) {
                    viewModel.getFriends(value.size)
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun FriendItem(
    friend: Friend,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    with(friend) {
        Row(modifier.fillMaxWidth()) {
            Image(
                painter = rememberImagePainter(
                    data = photoUrl,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.photo_placeholder)
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(56.dp)
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = "$firstName $lastName",
                    color = BlueGrey900,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = Typography.subtitle1
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = domain,
                    color = BlueGrey300,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = Typography.body1
                )
            }

            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .width(32.dp)
            )

            var friendIsRemovedState by remember { mutableStateOf(false) }

            if (friendIsRemovedState) {
                IconButton(
                    onClick = {
                        viewModel.addFriend(id)
                        friendIsRemovedState = false
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_person_add_24),
                        contentDescription = null,
                        tint = BlueGrey300
                    )
                }
            } else {
                IconButton(
                    onClick = {
                        viewModel.deleteFriend(id)
                        friendIsRemovedState = true
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_people_24),
                        contentDescription = null,
                        tint = LightBlue500
                    )
                }
            }
        }
    }
}