package me.yihtseu.jishi.model.campus.edu


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EduLessonResult(
    val datas: Datas,
    val code: String
) {
    @Serializable
    data class Datas(
        @SerialName("cxxszhxqkb")
        val lessonTable: studentLessonTable
    ) {
        @Serializable
        data class studentLessonTable(
            val extParams: ExtParams,
            val pageSize: Int,
            val pageNumber: Int,
            val totalSize: Int,
            val rows: List<Row>
        ) {
            @Serializable
            data class ExtParams(
                val code: Int,
                val msg: String
            )

            @Serializable
            data class Row(
                @SerialName("ZYDM")
                val code: String,
                @SerialName("KKDWDM_DISPLAY")
                val schoolName: String,
                @SerialName("KSJC")
                val startLessonNum: Int,
                @SerialName("DWDM")
                val schoolCode: String,
                @SerialName("SKJS")
                val teacherName: String?,
                @SerialName("JSJC_DISPLAY")
                val startLessonName: String,
                @SerialName("KSLXDM_DISPLAY")
                val examDisplay: String,
                @SerialName("NJDM_DISPLAY")
                val gradeName: String,
                @SerialName("XNXQDM")
                val termInfo: String,
                @SerialName("KSJC_DISPLAY")
                val endLessonName: String,
                @SerialName("ZYDM_DISPLAY")
                val majorityName: String,
                @SerialName("KCM")
                val lessonName: String,
                @SerialName("JASDM")
                val classroomCode: String?,
                @SerialName("NJDM")
                val year: String,
                @SerialName("KCH")
                val onlineCode: String,
                @SerialName("JXLDM_DISPLAY")
                val buildingName: String,
                @SerialName("XS")
                val totalHours: Int,
                @SerialName("KCXZDM_DISPLAY")
                val attribute: String,
                @SerialName("JASMC")
                val classroomName: String?,
                @SerialName("JSJC")
                val endLessonNum: Int,
                @SerialName("XXXQDM")
                val zoneCode: String,
                @SerialName("KBSKSJ")
                val date: String,
                @SerialName("YPSJDD")
                val studyWeeks: String,
                @SerialName("XNXQDM_DISPLAY")
                val yearTermDisplay: String,
                @SerialName("KCLBDM_DISPLAY")
                val attributeDisplay: String,
                @SerialName("XXXQDM_DISPLAY")
                val zoneDisplay: String,
                @SerialName("ZCMC")
                val weekRange: String,
                @SerialName("DWDM_DISPLAY")
                val schoolDisplay: String,
                @SerialName("SKXQ_DISPLAY")
                val weekDay: String,
                @SerialName("JXLDM")
                val buildingCode: String?
            )
        }
    }
}