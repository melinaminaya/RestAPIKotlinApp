package br.com.autotrac.testnanoclient.adapter

import br.com.autotrac.testnanoclient.dataRemote.ParameterModel
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.TypeAdapter
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import kotlinx.serialization.descriptors.PrimitiveKind
import java.io.IOException
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
