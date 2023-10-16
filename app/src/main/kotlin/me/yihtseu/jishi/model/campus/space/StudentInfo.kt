package me.yihtseu.jishi.model.campus.space


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentInfo(
    @SerialName("XH")
    val xH: String,
    @SerialName("XM")
    val xM: String,
    @SerialName("DWMC")
    val dWMC: String,
    @SerialName("ZWLB")
    val zWLB: String,
    @SerialName("PYCC")
    val pYCC: String,
    @SerialName("ZYMC")
    val zYMC: String
)