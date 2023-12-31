package me.yihtseu.jishi.model.campus


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IStudentInfo(
    @SerialName("XH")
    val number: String,
    @SerialName("XM")
    val name: String,
    @SerialName("DWMC")
    val school: String,
    @SerialName("ZYMC")
    val major: String
)