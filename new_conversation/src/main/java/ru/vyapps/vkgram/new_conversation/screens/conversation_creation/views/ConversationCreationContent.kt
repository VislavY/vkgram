package ru.vyapps.vkgram.new_conversation.screens.conversation_creation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.core.theme.*
import ru.vyapps.vkgram.new_conversation.UserModel
import ru.vyapps.vkgram.new_conversation.R
import ru.vyapps.vkgram.new_conversation.screens.conversation_creation.models.ConversationCreationViewState

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun ConversationCreationContent(
    viewState: ConversationCreationViewState.Display,
    gallerySheetState: ModalBottomSheetState,
    onMemberRemoveClick: (UserModel) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (viewState.conversationPhoto == null) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                gallerySheetState.show()
                            }
                        },
                        modifier = Modifier
                            .background(
                                color = VKgramTheme.palette.secondary,
                                shape = CircleShape
                            )
                            .size(64.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                } else {
                    Image(
                        painter = rememberImagePainter(
                            data = viewState.conversationPhoto,
                            builder = {
                                crossfade(true)
                                placeholder(R.drawable.photo_placeholder_56)
                                transformations(CircleCropTransformation())
                            },
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch {
                                    gallerySheetState.show()
                                }
                            }
                            .clip(CircleShape)
                            .size(64.dp)
                    )
                }

                Spacer(Modifier.width(16.dp))

                ConversationNameTextField(
                    modifier = Modifier.weight(1f),
                    onTextChange = { text ->
                        viewState.conversationName = text
                    }
                )
            }

            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = VKgramTheme.palette.surface
            )

            Spacer(Modifier.height(16.dp))


            val memberCount = viewState.items.size
            val ending = when {
                memberCount == 0 -> "ов"
                memberCount == 1 -> ""
                memberCount <= 4 -> "а"
                else -> "ов"
            }
            Text(
                text = "$memberCount участник$ending",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = VKgramTheme.palette.secondary,
                fontWeight = FontWeight.Medium,
                style = VKgramTheme.typography.subtitle2
            )

            Spacer(Modifier.height(8.dp))

            LazyColumn {
                items(viewState.items) { member ->
                    MemberItem(
                        model = member,
                        onRemoveClick = {
                            onMemberRemoveClick(it)
                        }
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Preview
@Composable
fun ConversationCreationContent_Preview() {
    MainTheme {
        ConversationCreationContent(
            ConversationCreationViewState.Display(
                conversationPhoto = null,
                items = listOf(
                    UserModel(
                        id = 1,
                        domain = "Sample domain",
                        firstName = "It's",
                        lastName = "Sample",
                        isSelected = true
                    ),
                    UserModel(
                        id = 2,
                        domain = "Sample domain",
                        firstName = "It's",
                        lastName = "Sample",
                        isSelected = true
                    )
                ),
            ),
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
            onMemberRemoveClick = { }
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Preview
@Composable
fun DarkConversationCreationContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        ConversationCreationContent(
            ConversationCreationViewState.Display(
                conversationPhoto = null,
                items = listOf(
                    UserModel(
                        id = 1,
                        domain = "Sample domain",
                        firstName = "It's",
                        lastName = "Sample",
                        isSelected = true
                    ),
                    UserModel(
                        id = 2,
                        domain = "Sample domain",
                        firstName = "It's",
                        lastName = "Sample",
                        isSelected = true
                    )
                ),
            ),
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
            onMemberRemoveClick = { }
        )
    }
}