package br.com.autotrac.testnanoclient.handlers

import android.os.Build
import br.com.autotrac.testnanoclient.models.FilterModel
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.time.Instant
import java.util.Date

object ParseData {

    fun parseMessageList(json: String): FilterModel {
        val jsonObject: JsonObject = JsonParser.parseString(json).asJsonObject

        // Extract the values of param1, param3, and param4
        val param1: Long = jsonObject.get("msgCode").asLong
        val param2 = jsonObject.get("isForward").asBoolean
        val param3 =
            if (jsonObject.get("msgStatusNum") == null) 0 else jsonObject.get("msgStatusNum").asDouble.toInt()
        return FilterModel(param1 = param1, param2 = param2, param3 = param3, param4 = null)
    }

    fun convertFromTimeStampI(timeStamp: Long): Date {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Date.from(Instant.ofEpochMilli(timeStamp))
        } else {
            Date(timeStamp)
        }
    }

    fun convertFromTimeStamp(timeStamp: String?): Date? {
        return try {
            if (timeStamp != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val timestampDouble = timeStamp.toDouble()
                    Date.from(Instant.ofEpochMilli(timestampDouble.toLong()))
                } else {
                    Date(timeStamp.toDouble().toLong())
                }
            } else {
                null
            }
        } catch (e: NumberFormatException) {
            // Handle the case when parsing fails, e.g., invalid input string
            null
        }
    }
}