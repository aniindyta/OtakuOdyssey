package com.anindita.otakuodyssey.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anindita.otakuodyssey.R
import com.anindita.otakuodyssey.di.Injection
import com.anindita.otakuodyssey.model.AboutAnime
import com.anindita.otakuodyssey.ui.ViewModelFactory
import com.anindita.otakuodyssey.ui.common.UiState
import com.anindita.otakuodyssey.ui.components.AnimeItem
import com.anindita.otakuodyssey.ui.components.DotsIndicator
import com.anindita.otakuodyssey.ui.components.SearchBar
import com.anindita.otakuodyssey.ui.components.SectionText
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import java.util.Random

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllAnime()
            }
            is UiState.Success -> {
                Column(modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                ) {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = { newQuery ->
                            searchQuery = newQuery
                            viewModel.searchAnime(newQuery)
                        }
                    )
                    Banner()
                    SectionText(stringResource(R.string.section_trending))
                    AnimeRowContent(
                        aboutAnime = uiState.data,
                        modifier = Modifier,
                        navigateToDetail = navigateToDetail,
                    )
                    SectionText(stringResource(R.string.section_terbaru))
                    AnimeRowContent(
                        aboutAnime = uiState.data,
                        modifier = Modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(modifier: Modifier = Modifier) {
    val bannerImages = listOf(
        R.drawable.a1,
        R.drawable.a2,
        R.drawable.a3,
        R.drawable.a4,
        R.drawable.a5
    )

    val pagerState = rememberPagerState(pageCount = bannerImages.size)

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % pagerState.pageCount)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { currentPage ->
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .aspectRatio(1.5f),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box {
                        Image(
                            painter = painterResource(id = bannerImages[currentPage]),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        DotsIndicator(
                            modifier = Modifier
                                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                                .align(Alignment.BottomCenter),
                            pageCount = bannerImages.size,
                            currentPage = pagerState.currentPage
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeRowContent(
    aboutAnime: List<AboutAnime>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    val randomAnime = aboutAnime.shuffled(Random(System.currentTimeMillis())).take(5)

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(randomAnime) { data ->
            AnimeItem(title = data.anime.title, imageUrl = data.anime.imageUrl, modifier = Modifier
                .padding(4.dp)
                .clickable {
                    navigateToDetail(data.anime.id)
                })
        }
    }
}
