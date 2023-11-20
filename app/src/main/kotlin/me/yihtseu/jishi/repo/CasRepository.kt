package me.yihtseu.jishi.repo

import android.util.Base64
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import me.yihtseu.jishi.base.Client
import me.yihtseu.jishi.base.Endpoint
import me.yihtseu.jishi.base.Proxy
import me.yihtseu.jishi.model.campus.IStudentInfo
import me.yihtseu.jishi.utils.crypto.strEnc
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CasRepository @Inject constructor(
    val client: Client,
    val proxy: Proxy
) {
    private lateinit var lt: String
    private lateinit var execution: String
    private lateinit var event: String
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        allowStructuredMapKeys = true
        encodeDefaults = true
    }

    private suspend fun check(): Boolean = coroutineScope {
        val document = Jsoup.parse(client.get(login).await())
        lt = document.select(ltSelector).attr("value")
        execution = document.select(executionSelector).attr("value")
        event = document.select(eventSelector).attr("value")
        return@coroutineScope (lt.isEmpty() && execution.isEmpty() && event.isEmpty())
    }

    suspend fun checkLogin(username: String, passwd: String): Boolean = coroutineScope {
        if (proxy.shouldUseVpn && !proxy.hasLoginVpn) proxy.login(username, passwd)
        if (check()) return@coroutineScope true
        val rsa = strEnc("${username.trim()}${passwd.trim()}$lt", "1", "2", "3")
        client.post(
            login, mapOf(
                "rsa" to rsa,
                "ul" to username.trim().length.toString(),
                "pl" to passwd.trim().length.toString(),
                "sl" to "0",
                "lt" to lt,
                "execution" to execution,
                "_eventId" to event
            )
        ).await()
        return@coroutineScope check()
    }

    suspend fun fetchProfile(): IStudentInfo = coroutineScope {
        val data = client.post(profile, "{}").await()
        return@coroutineScope json.decodeFromString<List<IStudentInfo>>(data).first()
    }

    suspend fun fetchPicture(): ByteArray = coroutineScope {
        val data = client.post(picture, "{}").await()
        return@coroutineScope Base64.decode(data, Base64.DEFAULT)
    }

    companion object {
        val login = Endpoint(
            url = "https://cas.jlu.edu.cn/tpass/login",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a979ef2609c4be59c95ff222d46b/tpass/login"
        )
        val profile = Endpoint(
            url = "https://i.jlu.edu.cn/up/up/subgroup/infoPage",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a336f6641686ae13d915e462/up/up/subgroup/infoPage"
        )
        val picture = Endpoint(
            url = "https://i.jlu.edu.cn/up/up/subgroup/infoPagePic",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a336f6641686ae13d915e462/up/up/subgroup/infoPagePic"
        )
        val ltSelector = "#lt"
        val executionSelector = "#loginForm > input:nth-child(9)"
        val eventSelector = "#loginForm > input:nth-child(10)"
    }
}