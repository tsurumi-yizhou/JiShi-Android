package me.yihtseu.jishi.ui.page

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.yihtseu.jishi.ui.Navigation

@Composable
fun AppScreen() {
    val controller = rememberNavController()

    NavHost(controller, startDestination = Navigation.LoginScreen.id.toString()) {
        composable(Navigation.LoginScreen.id.toString()) {
            LoginScreen(controller)
        }
        composable(Navigation.HomeScreen.id.toString()) {
            HomeScreen(controller)
        }
        composable(Navigation.NewsScreen.id.toString()) {
            NewsScreen(controller)
        }
        composable(Navigation.SettingScreen.id.toString()) {
            SettingScreen(controller)
        }
        composable(Navigation.IdentifyScreen.id.toString()) {
            IdentifyScreen(controller)
        }
        composable(Navigation.CalendarScreen.id.toString()) {
            CalendarScreen(controller)
        }
        composable(Navigation.LibraryScreen.id.toString()) {
            LibraryScreen(controller)
        }
        composable(Navigation.ClassroomScreen.id.toString()) {
            ClassroomScreen(controller)
        }
        composable(Navigation.ScoreScreen.id.toString()) {
            ScoreScreen(controller)
        }
        composable(Navigation.CampusEmailScreen.id.toString()) {
            CampusEmailScreen(controller)
        }
        composable(Navigation.CampusCardScreen.id.toString()) {
            CampusCardScreen(controller)
        }
        composable(Navigation.AboutScreen.id.toString()) {
            AboutScreen(controller)
        }
        composable(Navigation.SubscriptionScreen.id.toString()) {
            SubscriptionScreen(controller)
        }
        composable(Navigation.LicenseScreen.id.toString()) {
            LicenseScreen(controller)
        }
    }
}