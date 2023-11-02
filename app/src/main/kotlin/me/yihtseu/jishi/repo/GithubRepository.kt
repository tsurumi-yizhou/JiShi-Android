package me.yihtseu.jishi.repo

import com.drake.net.Get
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import me.yihtseu.jishi.model.github.Contributor
import me.yihtseu.jishi.model.github.Release

object GithubRepository {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        allowStructuredMapKeys = true
        encodeDefaults = true
    }

    suspend fun fetchContributors(): List<Contributor> = coroutineScope {
        return@coroutineScope json.decodeFromString<List<Contributor>>(Get<String>(
            "https://api.github.com/repos/tsurumi-yizhou/JiShi-Android/contributors"
        ) {
            addHeader("X-GitHub-Api-Version", "2022-11-28")
            addHeader("Accept", "application/vnd.github+json")
        }.await()
        )
    }

    suspend fun fetchLatestRelease(channel: String): Release = coroutineScope {
        val releases = json.decodeFromString<List<Release>>(Get<String>(
            "https://api.github.com/repos/tsurumi-yizhou/JiShi-Android/releases"
        ) {
            addHeader("X-GitHub-Api-Version", "2022-11-28")
            addHeader("Accept", "application/vnd.github+json")
        }.await()
        )
        return@coroutineScope releases.last { it.tagName == channel }
    }
}