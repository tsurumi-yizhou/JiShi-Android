package me.yihtseu.jishi.model.campus.edu


import kotlinx.serialization.Serializable

@Serializable
data class Resource(
    val selector: String, val id: String, val title: String, val mod: String, val type: String
)