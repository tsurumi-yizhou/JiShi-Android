package me.yihtseu.jishi.repo

import com.drake.net.Get
import com.drake.net.Post
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import me.yihtseu.jishi.error.DataError
import me.yihtseu.jishi.model.campus.edu.*
import me.yihtseu.jishi.utils.time.weeksPast

object EduRepository {
    private val json = Json {
        prettyPrint = false
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        allowStructuredMapKeys = true
        encodeDefaults = true
    }

    private suspend fun getAppConfig(): AppConfig = coroutineScope {
        val resp = Get<String>(Api.appConfigUrl("wdkb")).await()
        return@coroutineScope json.decodeFromString<AppConfig>(resp)
    }

    private suspend fun setRole(id: String) = coroutineScope {
        Get<String>(Api.roleUrl(id)).await()
    }

    private suspend fun getTerm(): List<TermResult.Datas.Cxjcs.Row> = coroutineScope {
        val resp = Get<String>(Api.termUrl).await()
        return@coroutineScope json.decodeFromString<TermResult>(resp).datas.cxjcs.rows
    }

    suspend fun getLessons(): List<LessonResult.Datas.studentLessonTable.Row> = coroutineScope {
        setRole(getAppConfig().header.dropMenu.first().id)
        val term = getTerm().first()
        val week = weeksPast(term.startDate)

        val resultThisWeek = json.decodeFromString<LessonResult>(Get<String>(Api.lessonUrl(term.yearRange, term.term, week.toString())).await())
        if (resultThisWeek.code.toInt() != 0)   throw DataError()

        val resultNextWeek = json.decodeFromString<LessonResult>(Get<String>(Api.lessonUrl(term.yearRange, term.term, (week + 1).toString())).await())
        if (resultNextWeek.code.toInt() != 0)   throw DataError()

        return@coroutineScope resultThisWeek.datas.lessonTable.rows + resultNextWeek.datas.lessonTable.rows
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
        setRole(getAppConfig().header.dropMenu.first().id)

        val url = json.decodeFromString<Service>(Get<String>(Api.kxjscxUrl).await()).models.last().controls.first().url
        return@coroutineScope json.decodeFromString<BuildingResult>(Post<String>("https://iedu.jlu.edu.cn$url").await()).datas.code.rows
    }

    suspend fun getClassrooms(buildings: List<String>, date: String, start: Int, end: Int): List<RoomResult.Datas.Cxkxjs.Row> = coroutineScope {
        return@coroutineScope json.decodeFromString<RoomResult>(Post<String>(Api.cxkxjsUrl){
            Api.headers.forEach { (key, value) ->
                addHeader(key, value)
            }
            param("querySetting", "[]")
            param("JXLDM", buildings.joinToString(","))
            param("KXRQ", date)
            param("JSJC", end.toString())
            param("KSJC", start.toString())
            param("KXJC", start.toString())
            param("pageSize", "100")
            param("pageNumber", "1")
        }.await()).datas.cxkxjs.rows
    }
}