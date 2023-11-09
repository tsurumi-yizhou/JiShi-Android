package me.yihtseu.jishi.ui.framework

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R
import me.yihtseu.jishi.ui.Navigation

@Composable
fun BottomBar(id: String, controller: NavHostController) {
    BottomAppBar {
        NavigationBarItem(
            selected = id == Navigation.HomeScreen.id.toString(),
            alwaysShowLabel = id == Navigation.HomeScreen.id.toString(),
            label = {
                Text(text = stringResource(R.string.home))
            },
            icon = {
                Icon(Icons.Outlined.Home, null)
            },
            onClick = {
                controller.navigate(Navigation.HomeScreen.id.toString()) {
                    popUpTo(0)
                }
            }
        )
        NavigationBarItem(
            selected = id == Navigation.NewsScreen.id.toString(),
            alwaysShowLabel = id == Navigation.NewsScreen.id.toString(),
            label = {
                Text(text = stringResource(R.string.news))
            },
            icon = {
                Icon(Icons.Outlined.Newspaper, null)
            },
            onClick = {
                controller.navigate(Navigation.NewsScreen.id.toString()) {
                    popUpTo(0)
                }
            }
        )
        NavigationBarItem(
            selected = id == Navigation.SettingScreen.id.toString(),
            alwaysShowLabel = id == Navigation.SettingScreen.id.toString(),
            label = {
                Text(text = stringResource(R.string.setting))
            },
            icon = {
                Icon(Icons.Outlined.Settings, null)
            },
            onClick = {
                controller.navigate(Navigation.SettingScreen.id.toString()) {
                    popUpTo(0)
                }
            }
        )
    }
}