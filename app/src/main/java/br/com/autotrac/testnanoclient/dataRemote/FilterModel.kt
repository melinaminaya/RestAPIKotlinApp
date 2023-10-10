package br.com.autotrac.testnanoclient.dataRemote

/**
 * Objeto de envio de filtro de mensagens para receber a lista de mensagens
 * da requisição [br.com.autotrac.testnanoclient.consts.ApiEndpoints.REQ_MESSAGE_LIST].
 */
data class FilterModel(
    val param1: Any?,
    val param2:Any,
    val param3:Any?,
    val param4: Any?
)