package me.yihtseu.jishi.model.chaoxing


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Homework(
    val result: Int,
    val msg: String,
    val data: Data,
    val errorMsg: String
) {
    @Serializable
    data class Data(
        val ext: Ext,
        val readingDuration: Int,
        val activeList: List<Active>
    ) {
        @Serializable
        data class Ext(
            @SerialName("_from_")
            val from: String
        )

        @Serializable
        data class Active(
            val userStatus: Int,
            val nameTwo: String,
            val groupId: Int,
            val source: Int?,
            val isLook: Int,
            val type: Int,
            val releaseNum: Int,
            val attendNum: Int,
            val activeType: Int,
            val logo: String,
            val nameOne: String,
            val startTime: Long,
            val id: Long,
            val endTime: Long,
            val status: Int,
            val nameFour: String,
            val extraInfo: ExtraInfo?,
            val otherId: String?
        ) {
            @Serializable
            data class ExtraInfo(
                val topicId: String,
                val groupId: String
            )
        }
    }
}