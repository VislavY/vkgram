package me.vislavy.vkgram.search.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@Composable
fun SearchLoadingContent(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
        LazyColumn() {
            items(10) {
                PlaceholderFoundItem()
            }
        }
    }
}

@Preview
@Composable
fun PreviewSearchLoadingContent() {
    MainTheme {
        SearchLoadingContent()
    }
}