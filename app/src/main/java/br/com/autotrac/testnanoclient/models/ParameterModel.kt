package br.com.autotrac.testnanoclient.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Objeto de retorno da requisição [br.com.autotrac.testnanoclient.consts.ApiEndpoints.REQ_GET_MCT_PARAMETERS].
 * @param number Número do parâmetro.
 * @param value Valor do parâmetro.
 * @see br.com.autotrac.testnanoclient.consts.ParameterValues.MctM0M9Params
 */
data class ParameterModel(
    @JsonProperty("number") val number: Int,
    @JsonProperty("value") val value: String,
    )

