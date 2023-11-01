package me.yihtseu.jishi.repo

import android.util.Log
import com.drake.net.Get
import com.drake.net.Post
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import me.yihtseu.jishi.model.campus.edu.*

object EduRepository {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        allowStructuredMapKeys = true
        encodeDefaults = true
    }

    private suspend fun getAppConfig(service: String): AppConfig = coroutineScope {
        val resp = Get<String>(Api.appConfigUrl(service)).await()
        return@coroutineScope json.decodeFromString<AppConfig>(resp)
    }

    private suspend fun setRole(id: String) = coroutineScope {
        Get<String>(Api.roleUrl(id)).await()
    }

    suspend fun getTerm(): TermResult.Datas.Cxjcs = coroutineScope {
        setRole(getAppConfig("wdkb").header.dropMenu.first().id)
        val resp = Get<String>(Api.termUrl).await()
        return@coroutineScope json.decodeFromString<TermResult>(resp).datas.cxjcs
    }

    suspend fun getLessons(year: String, term: String): List<LessonResult.Datas.studentLessonTable.Row> = coroutineScope {
        setRole(getAppConfig("wdkb").header.dropMenu.first().id)

        val result = json.decodeFromString<LessonResult>(Get<String>(Api.lessonUrl(year, term)).await())

        return@coroutineScope result.datas.lessonTable.rows
    }

    suspend fun getLessons(year: String, term: String, week: Int): List<LessonResult.Datas.studentLessonTable.Row> = coroutineScope {
        setRole(getAppConfig("wdkb").header.dropMenu.first().id)

        val result = json.decodeFromString<LessonResult>(Get<String>(Api.lessonUrl(year, term, week.toString())).await())

        return@coroutineScope result.datas.lessonTable.rows
    }

    suspend fun getBuildings(): List<BuildingResult.Datas.Code.Row> = coroutineScope {
        Get<String>(Api.indexUrl).await()
        Post<String>(Api.cxjwggbbdqxUrl){
            Api.headers.forEach { (key, value) ->
                addHeader(key, value)
            }
            param("SFQY", "1")
            param("APP", "4768402106681759")
        }.await()
        Get<String>(Api.appConfigUrl("kxjas")).await()
        Get<String>(Api.kxjasUrl).await()
        setRole(getAppConfig("kxjas").header.dropMenu.first().id)

        val url = json.decodeFromString<Service>(Get<String>(Api.kxjscxUrl).await()).search()

        val data = Post<String>("https://iedu.jlu.edu.cn${url}"){
            Api.headers.forEach { (key, value) ->
                addHeader(key, value)
            }
        }.await()

        return@coroutineScope json.decodeFromString<BuildingResult>(data).datas.code.rows

    }

    suspend fun getClassrooms(zone: String, building: String, date: String, start: Int, end: Int): List<RoomResult.Datas.Cxkxjs.Row> = coroutineScope {
        return@coroutineScope json.decodeFromString<RoomResult>(Post<String>(Api.cxkxjsUrl) {
            Api.headers.forEach { (key, value) ->
                addHeader(key, value)
            }
            param("XXXQDM", zone)
            param("JXLDM", building)
            param("KXRQ", date)
            param("JSJC", end.toString())
            param("KSJC", start.toString())
            param("KXJC", start.toString())
            param("querySetting", "[]")
            param("pageSize", "100")
            param("pageNumber", "1")
        }.await()).datas.cxkxjs.rows
    }

    suspend fun getScore(): List<ScoreResult.Datas.Xscjcx.Row> = coroutineScope {
        setRole(getAppConfig("cjcx").header.dropMenu.first().id)
        return@coroutineScope json.decodeFromString<ScoreResult>(Get<String>(Api.scoreUrl).await()).datas.xscjcx.rows.apply {
            Log.d("score", toString())
        }
    }
}