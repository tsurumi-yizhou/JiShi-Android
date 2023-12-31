package me.yihtseu.jishi.repo

import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import me.yihtseu.jishi.base.Client
import me.yihtseu.jishi.base.Endpoint
import me.yihtseu.jishi.model.campus.EduExamResult
import me.yihtseu.jishi.model.campus.edu.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EduRepository @Inject constructor(
    val client: Client
) {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        allowStructuredMapKeys = true
        encodeDefaults = true
    }

    private lateinit var wdkb: String
    private lateinit var kxjas: String
    private lateinit var cjcx: String
    private lateinit var wdksap: String

    suspend fun init() = coroutineScope {
        wdkb = json.decodeFromString<EduAppConfig>(client.get(wdkbConfig).await()).header.dropMenu.first().id
        kxjas = json.decodeFromString<EduAppConfig>(client.get(kxjasConfig).await()).header.dropMenu.first().id
        cjcx = json.decodeFromString<EduAppConfig>(client.get(cjcxConfig).await()).header.dropMenu.first().id
        wdksap = json.decodeFromString<EduAppConfig>(client.get(wdksapConfig).await()).header.dropMenu.first().id
    }

    private suspend fun setRole(id: String) = coroutineScope {
        client.get(role, mapOf("ROLEID" to id)).await()
    }

    suspend fun getTerm(): EduTermResult.Datas.Cxjcs = coroutineScope {
        setRole(wdkb)
        val resp = client.get(term).await()
        return@coroutineScope json.decodeFromString<EduTermResult>(resp).datas.cxjcs
    }

    suspend fun getLessons(year: String, term: String): List<EduLessonResult.Datas.studentLessonTable.Row> =
        coroutineScope {
        setRole(wdkb)
            val result = json.decodeFromString<EduLessonResult>(
            client.get(
                lessons, mapOf(
                    "XNXQDM" to "$year-$term"
                )
            ).await()
        )
        return@coroutineScope result.datas.lessonTable.rows
    }

    suspend fun getLessons(year: String, term: String, week: Int): List<EduLessonResult.Datas.studentLessonTable.Row> =
        coroutineScope {
        setRole(wdkb)
            val result = json.decodeFromString<EduLessonResult>(
            client.get(
                lessons, mapOf(
                    "XNXQDM" to "$year-$term",
                    "SKZC" to week.toString()
                )
            ).await()
        )
        return@coroutineScope result.datas.lessonTable.rows
    }

    suspend fun getBuildings(): List<EduBuildingResult.Datas.Code.Row> = coroutineScope {
        client.get(index).await()
        //client.post(buildingsPrepare, mapOf("SFQY" to "1", "APP" to "4768402106681759")).await()
        init()
        client.get(buildingsSet).await()
        setRole(kxjas)
        val url = json.decodeFromString<EduService>(client.get(buildingsQuery).await()).search()
        val data = client.post(
            Endpoint(
                "https://iedu.jlu.edu.cn${url}",
                vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b${url}",
                headers = headers
            ),
            emptyMap()
        ).await()
        return@coroutineScope json.decodeFromString<EduBuildingResult>(data).datas.code.rows
    }

    suspend fun getClassrooms(
        zone: String,
        building: String,
        date: String,
        start: Int,
        end: Int
    ): List<EduRoomResult.Datas.Cxkxjs.Row> = coroutineScope {
        return@coroutineScope json.decodeFromString<EduRoomResult>(
            client.post(
                classrooms, mapOf(
                    "XXXQDM" to zone,
                    "JXLDM" to building,
                    "KXRQ" to date,
                    "JSJC" to end.toString(),
                    "KSJC" to start.toString(),
                    "KXJC" to start.toString(),
                    "querySetting" to "[]",
                    "pageSize" to "100",
                    "pageNumber" to "1",
                )
            ).await()
        ).datas.cxkxjs.rows
    }

    suspend fun getScore(): List<EduScoreResult.Datas.Xscjcx.Row> = coroutineScope {
        setRole(cjcx)
        return@coroutineScope json.decodeFromString<EduScoreResult>(client.get(score).await()).datas.xscjcx.rows
    }

    suspend fun getExams(): List<EduExamResult.Datas.Cxxsksap.Row> = coroutineScope {
        setRole(wdksap)
        return@coroutineScope json.decodeFromString<EduExamResult>(
            client.post(
                exam, mapOf(
                    "requestParamStr" to "{\"XNXQDM\":\"2023-2024-1\"}"
                )
            ).await()
        ).datas.cxxsksap.rows
    }

    companion object {
        val headers = mapOf(
            "Host" to "iedu.jlu.edu.cn",
            "User-Agent" to "JiShi-Android",
            "Accept-Encoding" to "gzip to deflate to br",
            "X-Requested-With" to "XMLHttpRequest",
            "Origin" to "https://iedu.jlu.edu.cn",
            "Connection" to "keep-alive",
            "Referer" to "https://iedu.jlu.edu.cn/jwapp/sys/kxjas/*default/index.do"
        )
        val wdkbConfig = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/funauthapp/api/getAppConfig/wdkb-4770397878132218.do",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/funauthapp/api/getAppConfig/wdkb-4770397878132218.do",
            headers = headers
        )
        val cjcxConfig = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/funauthapp/api/getAppConfig/cjcx-4770397878132218.do",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/funauthapp/api/getAppConfig/cjcx-4770397878132218.do",
            headers = headers
        )
        val kxjasConfig = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/funauthapp/api/getAppConfig/kxjas-4770397878132218.do",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/funauthapp/api/getAppConfig/kxjas-4770397878132218.do",
            headers = headers
        )
        val wdksapConfig = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/funauthapp/api/getAppConfig/studentWdksapApp-4768687067472349.do",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/funauthapp/api/getAppConfig/studentWdksapApp-4768687067472349.do",
            headers = headers
        )
        val role = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/jwpubapp/pub/setJwCommonAppRole.do",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/jwpubapp/pub/setJwCommonAppRole.do",
            headers = headers
        )
        val index = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/emaphome/portal/index.do",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/emaphome/portal/index.do",
            headers = headers
        )
        val buildingsPrepare = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/jwpubapp/modules/bb/cxjwggbbdqx.do",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/jwpubapp/modules/bb/cxjwggbbdqx.do",
            headers = headers
        )
        val buildingsSet = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/emappagelog/config/kxjas.do",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/emappagelog/config/kxjas.do",
            headers = headers
        )
        val buildingsQuery = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/kxjas/modules/kxjscx.do?*json=1",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/kxjas/modules/kxjscx.do?*json=1",
            headers = headers
        )
        val classrooms = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/kxjas/modules/kxjscx/cxkxjs.do",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/kxjas/modules/kxjscx/cxkxjs.do",
            headers = headers
        )
        val score = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/cjcx/modules/cjcx/xscjcx.do?querySetting=&pageSize=100&pageNumber=1",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/cjcx/modules/cjcx/xscjcx.do?querySetting=&pageSize=100&pageNumber=1",
            headers = headers
        )
        val lessons = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/wdkb/modules/xskcb/cxxszhxqkb.do",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/wdkb/modules/xskcb/cxxszhxqkb.do",
            headers = headers
        )
        val term = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/wdkb/modules/jshkcb/cxjcs.do",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/wdkb/modules/jshkcb/cxjcs.do",
            headers = headers
        )
        val exam = Endpoint(
            "https://iedu.jlu.edu.cn/jwapp/sys/studentWdksapApp/WdksapController/cxxsksap.do",
            vpnUrl = "https://vpn.jlu.edu.cn/https/44696469646131313237446964696461a37df87d4dc2a702825ee37999669b/jwapp/sys/studentWdksapApp/WdksapController/cxxsksap.do",
            headers = headers
        )
    }
}