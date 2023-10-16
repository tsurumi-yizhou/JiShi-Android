package me.yihtseu.jishi.model.chaoxing


import kotlinx.serialization.Serializable

@Serializable
data class LessonList(
    val result: Int,
    val msg: String,
    val data: Data
) {
    @Serializable
    data class Data(
        val curriculum: Curriculum,
        val lessonArray: List<String>
    ) {
        @Serializable
        data class Curriculum(
            val fid: Int,
            val realCurrentWeek: Int,
            val courseTypeName: String,
            val earlyMorningTime: String,
            val firstWeekDateReal: Long,
            val userFid: Int,
            val uuid: String,
            val sectionTime: String,
            val puid: Int,
            val earlyMorningSection: String,
            val lessonLength: Int,
            val curriculumCount: Int,
            val morningTime: String,
            val schoolYear: String,
            val currentWeek: Int,
            val id: Int,
            val isHasEduLesson: Int,
            val afternoonTime: String,
            val existMaxLength: Int,
            val morningSection: String,
            val getLessonFromCache: Int,
            val maxWeek: Int,
            val updateTime: Long,
            val sort: Int,
            val userName: String,
            val firstWeekDate: Long,
            val insertTime: Long,
            val breakTime: String,
            val eveningSection: String,
            val afternoonSection: String,
            val eveningTime: String,
            val semester: Int,
            val maxLength: Int,
            val lessonTimeConfigArray: List<String>
        )
    }
}