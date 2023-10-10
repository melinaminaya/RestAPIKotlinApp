package br.com.autotrac.testnanoclient.dataRemote

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Objeto de retorno da requisição [br.com.autotrac.testnanoclient.consts.ApiEndpoints.REQ_GET_MCT_PARAMETERS].
 */
data class ParameterModel(

    @JsonProperty("code") val code: String,
    @JsonProperty("number") val number:String,
    @JsonProperty("oldValue") val oldValue : String?,
    @JsonProperty("type") var type: String,
    @JsonProperty("value") val value: String,


    )

