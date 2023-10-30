package me.yihtseu.jishi.model.github


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val url: String,
    val type: String,
    val name: String,
    val company: String,
    val blog: String,
    val location: String,
    val email: String,
    val bio: String,
)