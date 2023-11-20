package me.yihtseu.jishi.base

import android.net.Uri
import com.drake.net.Get
import com.drake.net.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import me.yihtseu.jishi.utils.crypto.encrypt
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Client @Inject constructor(
    private val proxy: Proxy
) {
    private val client = OkHttpClient.Builder()
        .followRedirects(true)
        .followSslRedirects(true)
        .retryOnConnectionFailure(false)
        .cookieJar(cookieJar)
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .build()

    fun get(endpoint: Endpoint, queries: Map<String, String> = emptyMap()): Deferred<String> {
        val query = if (queries.isNotEmpty()) {
            "?" + queries.map { "${it.key}=${it.value}" }.joinToString("&")
        } else ""
        val url = if (proxy.shouldUseVpn) {
            endpoint.vpnUrl + query
        } else {
            endpoint.url + query
        }
        return CoroutineScope(Dispatchers.IO).async {
            return@async Get<String>(url).await()
            /*          return@async client.newCall(Request.Builder().apply {
                            if (proxy.shouldUseVpn) url(endpoint.vpnUrl)
                            else url(endpoint.url)
                            endpoint.headers.forEach { (key, value) ->
                                addHeader(key, value)
                            }
                        }.build()).execute().body?.string().orEmpty() */
        }
    }

    fun get(url: String, headers: Map<String, String> = emptyMap()): Deferred<String> {
        return CoroutineScope(Dispatchers.IO).async {
            return@async Get<String>(if (proxy.shouldUseVpn) encrypt(Uri.parse(url)) else url).await()
        }
    }

    fun post(endpoint: Endpoint, params: Map<String, String>): Deferred<String> {
        return CoroutineScope(Dispatchers.IO).async {
            return@async Post<String>(if (proxy.shouldUseVpn) endpoint.vpnUrl else endpoint.url) {
                endpoint.headers.forEach { key, value ->
                    addHeader(key, value)
                }
                addHeader("Content-Type", "application/x-www-form-urlencoded")
                params.forEach { key, value ->
                    param(key, value)
                }
            }.await()
            /*            val request = Request.Builder().apply {
                            url(endpoint.url)
                            endpoint.headers.forEach { (key, value) ->
                                addHeader(key, value)
                            }
                            addHeader("Content-Type", "application/x-www-form-urlencoded")
                            post(FormBody.Builder().apply {
                                params.forEach { (key, value) ->
                                    addEncoded(key, URLEncoder.encode(value, "UTF-8"))
                                }
                            }.build())
                        }.build()
                        return@async client.newCall(request).execute().body?.string().orEmpty()*/
        }
    }

    fun post(endpoint: Endpoint, json: String): Deferred<String> {
        return CoroutineScope(Dispatchers.IO).async {
            return@async Post<String>(if (proxy.shouldUseVpn) endpoint.vpnUrl else endpoint.url) {
                endpoint.headers.forEach { key, value ->
                    addHeader(key, value)
                }
                addHeader("Content-Type", "application/json")
                json(json)
            }.await()
            /*            return@async client.newCall(Request.Builder().apply {
                            url(endpoint.url)
                            endpoint.headers.forEach { (key, value) ->
                                addHeader(key, value)
                            }
                            addHeader("Content-Type", "application/json")
                            post(RequestBody.Companion.create("application/json".toMediaType(), json))
                        }.build()).execute().body?.string().orEmpty()*/
        }
    }

    fun post(url: String, headers: Map<String, String>): Deferred<String> {
        return CoroutineScope(Dispatchers.IO).async {
            return@async Post<String>(if (proxy.shouldUseVpn) encrypt(Uri.parse(url)) else url) {
                headers.forEach { key, value ->
                    addHeader(key, value)
                }
            }.await()
        }
    }

    companion object {
        val timeout = 15L
        val storage = mutableMapOf<HttpUrl, MutableList<Cookie>>()

        object cookieJar : CookieJar {
            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                return storage[url] ?: emptyList()
            }

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                storage[url]?.addAll(cookies)
            }

        }
    }
}