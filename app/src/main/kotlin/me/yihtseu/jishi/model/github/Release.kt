package me.yihtseu.jishi.model.github


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Release(
    val url: String,
    @SerialName("assets_url")
    val assetsUrl: String,
    @SerialName("upload_url")
    val uploadUrl: String,
    @SerialName("html_url")
    val htmlUrl: String,
    val id: Int,
    val author: Author,
    @SerialName("node_id")
    val nodeId: String,
    @SerialName("tag_name")
    val tagName: String,
    @SerialName("target_commitish")
    val targetCommitish: String,
    val name: String,
    val draft: Boolean,
    val prerelease: Boolean,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("published_at")
    val publishedAt: String,
    val assets: List<Asset>,
    @SerialName("tarball_url")
    val tarballUrl: String,
    @SerialName("zipball_url")
    val zipballUrl: String,
    val body: String
) {
    @Serializable
    data class Author(
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
        val siteAdmin: Boolean
    )

    @Serializable
    data class Asset(
        val url: String,
        val id: Int,
        @SerialName("node_id")
        val nodeId: String,
        val name: String,
        val label: String,
        val uploader: Uploader,
        @SerialName("content_type")
        val contentType: String,
        val state: String,
        val size: Int,
        @SerialName("download_count")
        val downloadCount: Int,
        @SerialName("created_at")
        val createdAt: String,
        @SerialName("updated_at")
        val updatedAt: String,
        @SerialName("browser_download_url")
        val browserDownloadUrl: String
    ) {
        @Serializable
        data class Uploader(
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
            val siteAdmin: Boolean
        )
    }
}