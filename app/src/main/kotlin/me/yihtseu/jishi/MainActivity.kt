package me.yihtseu.jishi

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.drake.net.NetConfig
import com.drake.net.cookie.PersistentCookieJar
import com.drake.net.okhttp.setConverter
import dagger.hilt.android.AndroidEntryPoint
import me.yihtseu.jishi.model.jishi.DataStore
import me.yihtseu.jishi.ui.page.AppScreen
import me.yihtseu.jishi.ui.theme.AppTheme
import me.yihtseu.jishi.utils.network.JsonConverter
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, PushService::class.java))

        startService(Intent(this, PushService::class.java))

        DataStore.initialize(application)

        NetConfig.initialize {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            cookieJar(PersistentCookieJar(applicationContext))
            setConverter(JsonConverter())
        }

        setContent {
            AppTheme {
                AppScreen()
            }
        }
    }
}