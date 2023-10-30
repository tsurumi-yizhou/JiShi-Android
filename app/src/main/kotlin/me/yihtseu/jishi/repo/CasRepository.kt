package me.yihtseu.jishi.repo

import com.drake.net.Get
import com.drake.net.Post
import kotlinx.coroutines.coroutineScope
import me.yihtseu.jishi.utils.crypto.strEnc
import org.jsoup.Jsoup

object CasRepository {
    private lateinit var lt: String
    private lateinit var execution: String
    private lateinit var event: String

    suspend fun check(): Boolean = coroutineScope {
        val document = Jsoup.parse(Get<String>("https://cas.jlu.edu.cn/tpass/login").await())
        lt = document.select("#lt").attr("value")
        execution = document.select("#loginForm > input:nth-child(9)").attr("value")
        event = document.select("#loginForm > input:nth-child(10)").attr("value")
        return@coroutineScope (lt.isEmpty() && execution.isEmpty() && event.isEmpty())
    }

    suspend fun checkLogin(username: String, passwd: String): Boolean = coroutineScope {
            if (check()) return@coroutineScope true
            val rsa = strEnc("${username.trim()}${passwd.trim()}$lt", "1", "2", "3")
            Post<String>("https://cas.jlu.edu.cn/tpass/login") {
                addHeader("Content-Type", "application/x-www-form-urlencoded")
                param("rsa", rsa)
                param("ul", username.trim().length.toString())
                param("pl", passwd.trim().length.toString())
                param("sl", "0")   // for email login
                param("lt", lt)
                param("execution", execution)
                param("_eventId", event)
            }.await()
            return@coroutineScope check()
    }
}