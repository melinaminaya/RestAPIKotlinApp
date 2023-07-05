package com.example.nanoclientkotlin.dataRemote

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@JsonIgnoreProperties(ignoreUnknown = true)
data class FormList(

    @JsonProperty("m_code") val code: Long,
    @JsonProperty("m_version") val version:Int,
    @JsonProperty("m_createdTime") var createdTime: String,
    @JsonProperty("m_definition") val definition: String,
    @JsonProperty("m_type") val type : Int,
    @JsonProperty("m_number") val number:Int,
    @JsonProperty("m_formReply") val formReply:Int,
    @JsonProperty("m_codeFormGroup") val codeFormGroup:Long,

    ) {

    private val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault())

    var createdTimeDt: Date? = null
        @JsonGetter("createdTime")
        get() = field ?: parseCreatedTime(createdTime)

    private fun parseCreatedTime(createdTime: String?): Date? {
        return createdTime?.let {
            try {
                dateFormat.parse(it)
            } catch (e: Exception) {
                null
            }
        }
    }
}

