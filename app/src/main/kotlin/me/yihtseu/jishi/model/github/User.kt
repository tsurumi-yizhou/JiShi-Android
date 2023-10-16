package me.yihtseu.jishi.model.github


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val login: String,
    val id: Int,
    @SerialName("node_id")
    val nodeId: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("gravatar_id")
    val gravatarId: String,
    val url: String,
    @SerialName("html_url")
    val htmlUrl: String,
    @SerialName("followers_url")
    val followersUrl: String,
    @SerialName("following_url")
    val followingUrl: String,
    @SerialName("gists_url")
    val gistsUrl: String,
    @SerialName("starred_url")
    val starredUrl: String,
    @SerialName("subscriptions_url")
    val subscriptionsUrl: String,
    @SerialName("organizations_url")
    val organizationsUrl: String,
    @SerialName("repos_url")
    val reposUrl: String,
    @SerialName("events_url")
    val eventsUrl: String,
    @SerialName("received_events_url")
    val receivedEventsUrl: String,
    val type: String,
    @SerialName("site_admin")
    val siteAdmin: Boolean,
    val name: String,
    val company: String,
    val blog: String,
    val location: String,
    val email: String,
    val hireable: String,
    val bio: String,
    @SerialName("twitter_username")
    val twitterUsername: String,
    @SerialName("public_repos")
    val publicRepos: Int,
    @SerialName("public_gists")
    val publicGists: Int,
    val followers: Int,
    val following: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)