package br.com.autotrac.testnanoclient.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Objeto de retorno da requisição [br.com.autotrac.testnanoclient.consts.ApiEndpoints.REQ_GET_POSITION_LAST]
 */
data class LastPosition(
    @JsonProperty("code") val code: Long?,
    @JsonProperty("latitude") val latitude:Long?,
    @JsonProperty("longitude") var longitude: Long?,
    @JsonProperty("altitude") val altitude: Float?,
    @JsonProperty("positionTime") val positionTime : String?,
    @JsonProperty("speed") val speed:Long?,
    @JsonProperty("heading") val heading:Float?,
    @JsonProperty("aging") val aging:Long?,
    @JsonProperty("accuracy") val accuracy:Long?,
    @JsonProperty("lastUpdTime") val lastUpdTime:String?,
    @JsonProperty("sourceType") val sourceType:Long?,
)