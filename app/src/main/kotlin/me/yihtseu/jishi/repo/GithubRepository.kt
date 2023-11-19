package me.yihtseu.jishi.repo

import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import me.yihtseu.jishi.base.Client
import me.yihtseu.jishi.base.Endpoint
import me.yihtseu.jishi.model.github.Contributor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    val client: Client
) {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        allowStructuredMapKeys = true
        encodeDefaults = true
    }

    suspend fun fetchContributors(): List<Contributor> = coroutineScope {
        return@coroutineScope json.decodeFromString<List<Contributor>>(client.get(contributor).await())
    }

    companion object {
        val contributor = Endpoint(
            url = "https://api.github.com/repos/tsurumi-yizhou/JiShi-Android/contributors",
            headers = mapOf(
                "Accept" to "application/vnd.github+json",
                "X-GitHub-Api-Version" to "2022-11-28"
            )
        )
    }
}