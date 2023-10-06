package br.com.autotrac.testnanoclient.dataRemote

import com.fasterxml.jackson.annotation.JsonProperty


data class ParameterModel(

    @JsonProperty("code") val code: String,
    @JsonProperty("number") val number:String,
    @JsonProperty("oldValue") val oldValue : String?,
    @JsonProperty("type") var type: String,
    @JsonProperty("value") val value: String,


    )

