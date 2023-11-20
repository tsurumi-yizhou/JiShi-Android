package me.yihtseu.jishi.model.campus.edu


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EduAppConfig(
    @SerialName("MODULES")
    val modules: List<MODULES>,
    @SerialName("HEADER")
    val header: HEADER,
    @SerialName("APP_ID")
    val appId: String
) {
    @Serializable
    data class RouteTitle(
        val xskcb: String
    )

    @Serializable
    data class MODULES(
        val title: String,
        val range: String,
        val route: String,
        val buttons: List<String>
    )

    @Serializable
    data class HEADER(
        val dropMenu: List<DropMenu>,
        val userInfo: UserInfo
    ) {
        @Serializable
        data class DropMenu(
            val id: String,
            val text: String,
            val active: Boolean
        )

        @Serializable
        class UserInfo
    }
}