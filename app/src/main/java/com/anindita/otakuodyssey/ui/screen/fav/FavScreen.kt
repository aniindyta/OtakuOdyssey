package com.anindita.otakuodyssey.ui.screen.fav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anindita.otakuodyssey.R
import com.anindita.otakuodyssey.di.Injection
import com.anindita.otakuodyssey.ui.ViewModelFactory
import com.anindita.otakuodyssey.ui.components.FavItem
import com.anindita.otakuodyssey.ui.screen.detail.DetailAnimeViewModel

@Composable
fun FavScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailAnimeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {
    val favoriteAnime by viewModel.getFavoriteAnime().collectAsState(emptyList())

    if (favoriteAnime.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(stringResource(R.string.no_favorite_items), textAlign = TextAlign.Center)
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            items(favoriteAnime) { data ->
                FavItem(title = data.anime.title, imageUrl = data.anime.imageUrl, desc = data.anime.desc , isFavorite = data.isFav)
            }
        }
    }
}