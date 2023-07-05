package com.example.nanoclientkotlin.dataRemote

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class ParameterModel(

    @JsonProperty("code") val code: String,
    @JsonProperty("number") val number:String,
    @JsonProperty("oldValue") val oldValue : String?,
    @JsonProperty("type") var type: String,
    @JsonProperty("value") val value: String,


    )

