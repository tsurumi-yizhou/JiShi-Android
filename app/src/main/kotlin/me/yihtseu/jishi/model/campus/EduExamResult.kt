package me.yihtseu.jishi.model.campus


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EduExamResult(
    val datas: Datas,
    val code: String
) {
    @Serializable
    data class Datas(
        val cxxsksap: Cxxsksap
    ) {
        @Serializable
        data class Cxxsksap(
            val extParams: ExtParams,
            val pageSize: Int,
            val pageNumber: Int,
            val totalSize: Int,
            val rows: List<Row>
        ) {
            @Serializable
            data class ExtParams(
                val code: Int,
                val msg: String?
            )

            @Serializable
            data class Row(
                @SerialName("JASMC")
                val classroom: String,
                @SerialName("KSSJMS")
                val datetime: String,
                @SerialName("BYBZ")
                val bYBZ: String,
                @SerialName("TSYYDM_DISPLAY")
                val disciplineDisplay: String,
                @SerialName("XXXQDM")
                val zone: String,
                @SerialName("SFXSKS_DISPLAY")
                val tipsDisplay: String?,
                @SerialName("ZWH")
                val seat: String?,
                @SerialName("SFXYAPKS")
                val useApk: String,
                @SerialName("BZ")
                val toKnow: String?,
                @SerialName("TSYYDM")
                val discipline: String?,
                @SerialName("KSMC")
                val examName: String,
                @SerialName("SFXYAPKS_DISPLAY")
                val useApkDisplay: String,
                @SerialName("SFXSKS")
                val examTips: String?,
                @SerialName("KSPT")
                val platform: String?,
                @SerialName("XNXQDM")
                val term: String,
                @SerialName("KSRQ")
                val date: String,
                @SerialName("XXXQDM_DISPLAY")
                val zoneDisplay: String,
                @SerialName("XNXQDM_DISPLAY")
                val termDisplay: String,
                @SerialName("SFAPSJ_DISPLAY")
                val sFAPSJDISPLAY: String,
                @SerialName("PTHYH")
                val platformMeetingNumber: String?,
                @SerialName("KSRWZT")
                val kSRWZT: String,
                @SerialName("KSRWID")
                val kSRWID: String,
                @SerialName("SFFBDD")
                val sFFBDD: String,
                @SerialName("SQZTDM")
                val sQZTDM: String?,
                @SerialName("KSDM")
                val kSDM: String,
                @SerialName("XSBH")
                val xSBH: String,
                @SerialName("SFFBSJ")
                val sFFBSJ: String,
                @SerialName("SFAPSJ")
                val sFAPSJ: String,
                @SerialName("PKSM")
                val tips: String?,
                @SerialName("KSPT_DISPLAY")
                val platformDisplay: String,
                @SerialName("KCM")
                val exam: String,
                @SerialName("KCH")
                val uuid: String
            )
        }
    }
}