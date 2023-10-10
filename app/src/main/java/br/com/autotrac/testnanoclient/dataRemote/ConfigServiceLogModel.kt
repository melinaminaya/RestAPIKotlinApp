package br.com.autotrac.testnanoclient.dataRemote

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Objeto de envio da requisição [br.com.autotrac.testnanoclient.consts.ApiEndpoints.REQ_CONFIG_SERVICE_LOG].
 */
data class ConfigServiceLogModel(
    @JsonProperty("enable") val enable: Boolean?,
    @JsonProperty("maxFileCount") val maxFileCountInt: Int?,
    @JsonProperty("maxFileSize") var maxFileSize: Long?,
)