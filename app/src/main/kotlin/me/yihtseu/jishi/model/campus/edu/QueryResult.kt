package me.yihtseu.jishi.model.campus.edu


import kotlinx.serialization.Serializable

@Serializable
data class QueryResult(
    val datas: Datas,
    val code: String
) {
    @Serializable
    data class Datas(
        val cxjwggbbdqx: Cxjwggbbdqx
    ) {
        @Serializable
        data class Cxjwggbbdqx(
            val totalSize: Int,
            val pageSize: Int,
            val rows: List<String>,
            val extParams: ExtParams
        ) {
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