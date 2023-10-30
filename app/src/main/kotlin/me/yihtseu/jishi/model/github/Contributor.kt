package me.yihtseu.jishi.model.github


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Contributor(
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val url: String,
    @SerialName("html_url")
    val htmlUrl: String,
    val type: String,
    val contributions: Int
)