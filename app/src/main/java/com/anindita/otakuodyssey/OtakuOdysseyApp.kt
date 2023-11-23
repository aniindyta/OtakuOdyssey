package com.anindita.otakuodyssey

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anindita.otakuodyssey.ui.navigation.NavigationItem
import com.anindita.otakuodyssey.ui.navigation.Screen
import com.anindita.otakuodyssey.ui.screen.all.AllScreen
import com.anindita.otakuodyssey.ui.screen.detail.DetailScreen
import com.anindita.otakuodyssey.ui.screen.fav.FavScreen
import com.anindita.otakuodyssey.ui.screen.home.HomeScreen
import com.anindita.otakuodyssey.ui.screen.profile.ProfileScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OtakuOdysseyApp(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold( bottomBar = { if (currentRoute != Screen.DetailAnime.route && currentRoute != Screen.Favorite.route ) {
        BottomBar(navController)
    } }, modifier = Modifier ) { innerPadding ->
        NavHost(navController = navController, startDestination = Screen.Home.route, modifier = Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { animeId ->
                        navController.navigate(Screen.DetailAnime.createRoute(animeId))
                    },
                    navigateToFavorite = {
                        navController.navigate(Screen.Favorite.route)
                    }
                )
            }
            composable(Screen.All.route) {
                AllScreen(
                    navigateToDetail = { animeId ->
                        navController.navigate(Screen.DetailAnime.createRoute(animeId))
                    },
                    navigateToFavorite = {
                        navController.navigate(Screen.Favorite.route)
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.Favorite.route) {
                FavScreen()
            }
            composable(
                route = Screen.DetailAnime.route,
                arguments = listOf(navArgument("animeId") { type = NavType.StringType }),
            ) {
                val id = it.arguments?.getString("animeId")
                DetailScreen(
                    animeId = id!!,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController, modifier: Modifier = Modifier) {
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

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
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}