package com.anindita.otakuodyssey

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anindita.otakuodyssey.model.AnimeData
import com.anindita.otakuodyssey.ui.components.AnimeItem
import com.anindita.otakuodyssey.ui.components.DotsIndicator
import com.anindita.otakuodyssey.ui.components.HomeSection
import com.anindita.otakuodyssey.ui.navigation.NavigationItem
import com.anindita.otakuodyssey.ui.navigation.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import java.util.Random

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OtakuOdysseyApp(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    Scaffold( bottomBar = { BottomBar(navController) }, modifier = Modifier ) { innerPadding ->
        NavHost(navController = navController, startDestination = Screen.Home.route, modifier = Modifier.padding(innerPadding)) {
//            composable(Screen.Home.route) {
//                HomeScreen()
//            }
//            composable(Screen.All.route) {
//                AllScreen()
//            }
//            composable(Screen.Profile.route) {
//                ProfileScreen()
//            }
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
                        .padding(24.dp)
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
fun AnimeRow(modifier: Modifier = Modifier) {
    val randomAnime = AnimeData.anime.shuffled(Random(System.currentTimeMillis())).take(5)

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(randomAnime) { anime ->
            AnimeItem(title = anime.title, imageUrl = anime.imageUrl, modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController, modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_all),
                icon = Icons.Default.Menu,
                screen = Screen.All
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = false,
                onClick = {
                }
            )
        }
    }
}

/*
Box(
modifier = modifier
.fillMaxSize()
.verticalScroll(rememberScrollState())
.padding(innerPadding),
contentAlignment = Alignment.TopStart
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Banner()
        HomeSection(
            title = stringResource(R.string.section_trending),
            content = { AnimeRow() }
        )
        HomeSection(
            title = stringResource(R.string.section_terbaru),
            content = { AnimeRow() }
        )
    }
}*/
