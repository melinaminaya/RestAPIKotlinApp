package com.example.nanoclientkotlin.dataRemote

import com.google.gson.JsonObject
import com.google.gson.JsonParser

object ParseData{

    fun parseMessageList(json:String):FilterModel{
        val jsonObject: JsonObject = JsonParser.parseString(json).asJsonObject

        // Extract the values of param1, param3, and param4
        val param1: Long = jsonObject.get("msgCode").asLong
        val param2 = jsonObject.get("isForward").asBoolean
        val param3 = if(jsonObject.get("msgStatusNum") == null) 0 else jsonObject.get("msgStatusNum").asDouble.toInt()
        return FilterModel(param1 = param1, param2 = param2, param3 = param3, param4 = null )
    }
}