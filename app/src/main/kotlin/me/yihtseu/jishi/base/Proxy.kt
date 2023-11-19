package me.yihtseu.jishi.base

import com.drake.net.Get
import com.drake.net.Post
import com.drake.net.exception.NetSocketTimeoutException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.json.JSONObject
import org.jsoup.Jsoup
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Proxy @Inject constructor() {
    var shouldUseVpn: Boolean = false
    var hasLoginVpn: Boolean = false

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .build()

    init {
        Thread {
            while (true) {
                runBlocking {
                    try {
                        Get<String>(testUrl).await()
                        shouldUseVpn = false
                    } catch (e: NetSocketTimeoutException) {
                        shouldUseVpn = true
                    } finally {
                        if (shouldUseVpn && !hasLoginVpn) {
                            login(
                                DataStore.getString("jlu_username")?.first()!!,
                                DataStore.getString("jlu_password")?.first()!!
                            )
                        }
                    }
                    delay(retryTime * 60 * 1000)
                }
            }
        }.start()
    }

    suspend fun login(username: String, password: String): Boolean = coroutineScope {
        val document = Jsoup.parse(Get<String>(preLoginUrl).await())
        val data = Post<String>(loginUrl) {
            param("auth_type", "local")
            param("username", username)
            param("sms_code", "")
            param("password", password)
            param("captcha", "")
            param("needCaptcha", "false")
            param("captcha_id", document.select(".captcha-div > input:nth-child(1)").attr("value"))
        }.await()
        shouldUseVpn = true
        hasLoginVpn = JSONObject(data).getBoolean("success")
        return@coroutineScope JSONObject(data).getBoolean("success")
    }

    companion object {
        val retryTime = 5L
        val testUrl = "https://ip.jlu.edu.cn"
        val preLoginUrl = "https://vpn.jlu.edu.cn/login"
        val loginUrl = "https://vpn.jlu.edu.cn/do-login"
    }
}