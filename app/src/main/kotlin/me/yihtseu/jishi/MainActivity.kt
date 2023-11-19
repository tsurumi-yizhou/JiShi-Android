package me.yihtseu.jishi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drake.net.NetConfig
import com.drake.net.cookie.PersistentCookieJar
import com.drake.net.okhttp.setConverter
import dagger.hilt.android.AndroidEntryPoint
import me.yihtseu.jishi.base.DataStore
import me.yihtseu.jishi.ui.page.pages
import me.yihtseu.jishi.ui.theme.AppTheme
import me.yihtseu.jishi.utils.network.JsonConverter
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var controller: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        DataStore.initialize(application)

        NetConfig.initialize {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            cookieJar(PersistentCookieJar(applicationContext))
            setConverter(JsonConverter())
        }

        setContent {
            controller = rememberNavController()
            AppTheme {
                NavHost(controller, startDestination = "login") {
                    pages.forEach { (id, page) ->
                        composable(route = id) {
                            page.show(controller)
                        }
                    }
                }
            }
        }
    }
}