package me.yihtseu.jishi.model.campus.edu


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EduTermResult(
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

            fun latest(): Row {
                for (row in rows) {
                    if (row.yearRange == "2023-2024" && row.term == "1") {
                        return row
                    }
                }
                throw Exception("No this term!")
            }
        }
    }
}