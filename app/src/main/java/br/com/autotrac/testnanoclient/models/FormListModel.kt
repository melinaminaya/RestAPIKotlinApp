package br.com.autotrac.testnanoclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

/**
 * Objeto de retorno da requisição [br.com.autotrac.testnanoclient.consts.ApiEndpoints.REQ_FORM_LIST]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class IntegrationForm(

    @JsonProperty("code") val code: Long,
    @JsonProperty("version") val version:Int,
    @JsonProperty("createdTime") var createdTime: Date,
    @JsonProperty("definition") val definition: String,
    @JsonProperty("type") val type : Int,
    @JsonProperty("number") val number:Int,
    @JsonProperty("formReply") val formReply:Int,
    @JsonProperty("codeFormGroup") val codeFormGroup:Long,

    )


