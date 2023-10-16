package me.yihtseu.jishi.model.chaoxing


import kotlinx.serialization.Serializable

@Serializable
data class ClassDetail(
    val result: Int,
    val msg: String,
    val data: Data,
    val errorMsg: String?
) {
    @Serializable
    data class Data(
        val studentcount: Int,
        val name: String,
        val creatoruserid: String,
        val course: Course,
        val courseid: Int
    ) {
        @Serializable
        data class Course(
            val data: List<Data>
        ) {
            @Serializable
            data class Data(
                val imageurl: String,
                val name: String,
                val id: Int
            )
        }
    }
}