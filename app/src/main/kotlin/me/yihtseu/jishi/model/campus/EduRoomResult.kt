package me.yihtseu.jishi.model.campus.edu

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EduRoomResult(
    val datas: Datas
) {
    @Serializable
    data class Datas(
        val cxkxjs: Cxkxjs
    ) {
        @Serializable
        data class Cxkxjs(
            val rows: List<Row>
        ) {
            @Serializable
            data class Row(
                @SerialName("JASMC") val name: String,
                @SerialName("JXLDM_DISPLAY") val building: String,
                @SerialName("KXSJ") val duration: String
            )
        }
    }
}
