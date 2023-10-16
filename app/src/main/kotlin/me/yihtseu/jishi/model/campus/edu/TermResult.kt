package me.yihtseu.jishi.model.campus.edu


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TermResult(
    val datas: Datas,
    val code: String
) {
    @Serializable
    data class Datas(
        val cxjcs: Cxjcs
    ) {
        @Serializable
        data class Cxjcs(
            val totalSize: Int,
            val rows: List<Row>,
            val extParams: ExtParams
        ) {
            @Serializable
            data class Row(
                @SerialName("XQ")
                val term: String,
                @SerialName("XN")
                val yearRange: String,
                @SerialName("XQKSRQ")
                val startDate: String
            )

            @Serializable
            data class ExtParams(
                val logId: String,
                val code: Int,
                val totalPage: Int
            )
        }
    }
}