package com.anindita.otakuodyssey.ui.screen.all

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anindita.otakuodyssey.di.Injection
import com.anindita.otakuodyssey.model.AboutAnime
import com.anindita.otakuodyssey.ui.ViewModelFactory
import com.anindita.otakuodyssey.ui.common.UiState
import com.anindita.otakuodyssey.ui.components.AnimeItem
import com.anindita.otakuodyssey.ui.components.SearchBar

@Composable
fun AllScreen(
    modifier: Modifier = Modifier,
    viewModel: AllViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit,
    navigateToFavorite: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllRewards()
            }
            is UiState.Success -> {
                Column(modifier = modifier
                    .fillMaxSize()
                ) {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = { newQuery ->
                            searchQuery = newQuery
                            viewModel.searchAnime(newQuery)
                        },
                        navigateToFavorite = {
                            navigateToFavorite()
                        }
                    )
                    AllAnimeContent(
                        aboutAnime = uiState.data,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun AllAnimeContent(
    aboutAnime: List<AboutAnime>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(aboutAnime) { data ->
            AnimeItem(
                title = data.anime.title,
                imageUrl = data.anime.imageUrl,
                modifier = Modifier.clickable {
                    navigateToDetail(data.anime.id)
                }
            )
        }
    }
}