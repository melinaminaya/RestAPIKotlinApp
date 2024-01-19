package br.com.autotrac.testnanoclient.adapter

import br.com.autotrac.testnanoclient.models.ParameterModel
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ParameterModelDeserializer : JsonDeserializer<ParameterModel> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ParameterModel {
        if (json?.isJsonObject == true) {
            val jsonObject = json.asJsonObject
            val number = jsonObject.getAsJsonPrimitive("number").asInt
            val value = jsonObject.getAsJsonPrimitive("value").asString
            return ParameterModel(number, value)
        }
        throw IllegalArgumentException("Invalid JSON format for converting to ParameterModel.")
    }
}
