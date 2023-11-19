package me.yihtseu.jishi.repo

import android.util.Base64
import kotlinx.coroutines.coroutineScope
import me.yihtseu.jishi.base.Client
import me.yihtseu.jishi.base.Endpoint
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HallRepository @Inject constructor(
    val client: Client
) {
    suspend fun qrcode(): ByteArray = coroutineScope {
        val resp = client.get(qrcode).await()
        val data = Jsoup.parse(resp).select(".ma").attr("src")
        return@coroutineScope Base64.decode(data.split(',').last(), Base64.DEFAULT)
    }

    companion object {
        val qrcode = Endpoint(
            "https://ehall.jlu.edu.cn/jlu_identitycode/IdentityCode_phone",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461af70fd640f86a11bd915e268c22b96fc/jlu_identitycode/IdentityCode_phone",
            headers = mapOf(
                "Host" to "ehall.jlu.edu.cn",
                "User-Agent" to "Mozilla/5.0 (Linux; Android 11; Pixel 3a) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Mobile Safari/537.36"
            )
        )
    }
}