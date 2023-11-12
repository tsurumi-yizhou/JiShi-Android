package me.yihtseu.jishi.model.jishi

import android.content.res.Resources.NotFoundException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Version(
    @SerialName("timestamp")
    val createdTimestamp: Long,
    @SerialName("download")
    val downloads: List<Download>
) {
    @Serializable
    data class Download(
        val name: String,
        val url: String
    )

    fun getUrl(name: String): String {
        for (download in downloads) {
            if (download.name == name) return download.url
        }
        throw NotFoundException()
    }
}
