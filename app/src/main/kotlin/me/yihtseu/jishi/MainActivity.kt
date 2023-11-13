package me.yihtseu.jishi

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drake.net.NetConfig
import com.drake.net.cookie.PersistentCookieJar
import com.drake.net.okhttp.setConverter
import dagger.hilt.android.AndroidEntryPoint
import me.yihtseu.jishi.model.jishi.DataStore
import me.yihtseu.jishi.ui.Navigation
import me.yihtseu.jishi.ui.page.*
import me.yihtseu.jishi.ui.theme.AppTheme
import me.yihtseu.jishi.utils.network.JsonConverter
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var controller: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataStore.initialize(application)

        NetConfig.initialize {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            cookieJar(PersistentCookieJar(applicationContext))
            setConverter(JsonConverter())
        }

        setContent {
            controller = rememberNavController()
            AppTheme {
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
                    composable("detail") {
                        NewsDetailScreen(controller)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}