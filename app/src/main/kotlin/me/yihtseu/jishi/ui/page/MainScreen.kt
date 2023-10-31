@file:OptIn(ExperimentalMaterial3Api::class)

package me.yihtseu.jishi.ui.page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.yihtseu.jishi.ui.Navigation
import me.yihtseu.jishi.ui.theme.typography

@Composable
fun MainScreen(
    controller: NavHostController
) {
    val pageController = rememberNavController()
    val currentPage = rememberSaveable { mutableIntStateOf(Navigation.HomeScreen.id) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = stringResource(currentPage.intValue))
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    selected = currentPage.intValue == Navigation.HomeScreen.id,
                    alwaysShowLabel = currentPage.intValue == Navigation.HomeScreen.id,
                    icon = {
                        Icon(Icons.Outlined.Home, null)
                    },
                    label = {
                        Text(text = stringResource(currentPage.intValue), style = typography.bodySmall)
                    },
                    onClick = {
                        currentPage.intValue = Navigation.HomeScreen.id
                        pageController.navigate(Navigation.HomeScreen.id.toString()) {
                            popUpTo(0)
                        }
                    }
                )
                NavigationBarItem(
                    selected = currentPage.intValue == Navigation.NewsScreen.id,
                    alwaysShowLabel = currentPage.intValue == Navigation.NewsScreen.id,
                    icon = {
                        Icon(Icons.Outlined.Newspaper, null)
                    },
                    label = {
                        Text(text = stringResource(currentPage.intValue), style = typography.bodySmall)
                    },
                    onClick = {
                        currentPage.intValue = Navigation.NewsScreen.id
                        pageController.navigate(Navigation.NewsScreen.id.toString()) {
                            popUpTo(0)
                        }
                    }
                )
                NavigationBarItem(
                    selected = currentPage.intValue == Navigation.SettingScreen.id,
                    alwaysShowLabel = currentPage.intValue == Navigation.SettingScreen.id,
                    icon = {
                        Icon(Icons.Outlined.Settings, null)
                    },
                    label = {
                        Text(text = stringResource(currentPage.intValue), style = typography.bodySmall)
                    },
                    onClick = {
                        currentPage.intValue = Navigation.SettingScreen.id
                        pageController.navigate(Navigation.SettingScreen.id.toString()) {
                            popUpTo(0)
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(pageController, startDestination = Navigation.HomeScreen.id.toString(), modifier = Modifier.padding(paddingValues)) {
            composable(Navigation.HomeScreen.id.toString()) { entry ->
                HomeScreen(controller, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection))
            }
            composable(Navigation.NewsScreen.id.toString()) { entry ->
                NewsScreen(controller, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection))
            }
            composable(Navigation.SettingScreen.id.toString()) { entry ->
                SettingScreen(controller, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection))
            }
        }
    }
}