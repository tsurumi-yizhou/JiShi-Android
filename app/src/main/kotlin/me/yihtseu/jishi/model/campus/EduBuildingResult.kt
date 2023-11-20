package me.yihtseu.jishi.model.campus.edu

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EduBuildingResult(
    val datas: Datas
) {
    @Serializable
    data class Datas(
        val code: Code
    ) {
        @Serializable
        data class Code(
            val rows: List<Row>
        ) {
            @Serializable
            data class Row(
                val name: String,
                val id: String,
                val otherFields: OtherFields
            ) {
                @Serializable
                data class OtherFields(
                    @SerialName("XXXQDM")
                    val zoneCode: String
                )
            }
        }
    }
}