package me.yihtseu.jishi.repo

import android.util.Base64
import com.drake.net.Get
import kotlinx.coroutines.coroutineScope
import me.yihtseu.jishi.model.campus.ehall.Api
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HallRepository @Inject constructor() {
    suspend fun qrcode(): ByteArray = coroutineScope {

            val resp = Get<String>(Api.identifyCodeUrl){
                Api.headers.forEach { (key, value) ->
                    addHeader(key, value)
                }
            }.await()
            val data = Jsoup.parse(resp).select(".ma").attr("src")
            return@coroutineScope Base64.decode(data.split(',').last(), Base64.DEFAULT)
    }
}