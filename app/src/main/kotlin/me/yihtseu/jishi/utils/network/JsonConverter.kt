@file:Suppress("UNCHECKED_CAST")

package me.yihtseu.jishi.utils.network

import com.drake.net.convert.NetConverter
import com.drake.net.exception.ConvertException
import com.drake.net.request.kType
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.Response
import java.lang.reflect.Type

class JsonConverter : NetConverter {

    private val json = Json {
        prettyPrint = false
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        allowStructuredMapKeys = true
        encodeDefaults = true
    }

    override fun <R> onConvert(succeed: Type, response: Response): R? {
        try {
            return NetConverter.onConvert(succeed, response)
        } catch (e: ConvertException) {
            return json.decodeFromString(Json.serializersModule.serializer(response.request.kType!!), response.body!!.string()) as R
        } catch (e: Exception) {
            return null
        }
    }
}