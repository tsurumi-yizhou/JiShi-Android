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
        composable(Navigation.MainScreen.id.toString()) {
            MainScreen(controller)
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
    }
}