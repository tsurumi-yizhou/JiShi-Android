package me.yihtseu.jishi.model.campus.edu


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScoreResult(
    val datas: Datas,
    val code: String
) {
    @Serializable
    data class Datas(
        val xscjcx: Xscjcx
    ) {
        @Serializable
        data class Xscjcx(
            val totalSize: Int,
            val pageNumber: Int,
            val pageSize: Int,
            val rows: List<Row>,
            val extParams: ExtParams
        ) {
            @Serializable
            data class Row(
                @SerialName("KKDWDM_DISPLAY")
                val school: String,
                @SerialName("XNXQDM")
                val term: String,
                @SerialName("XSKCH")
                val id: String,
                @SerialName("XFJD")
                val score: Double,
                @SerialName("XS")
                val grade: Double,
                @SerialName("KCM")
                val name: String,
                @SerialName("KCLBDM_DISPLAY")
                val type: String
            )

            @Serializable
            data class ExtParams(
                val logId: String,
                val code: Int,
                val totalPage: Int,
                val msg: String
            )
        }
    }
}