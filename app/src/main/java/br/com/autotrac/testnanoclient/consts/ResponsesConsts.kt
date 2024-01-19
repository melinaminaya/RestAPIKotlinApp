package br.com.autotrac.testnanoclient.consts

/**
 * Elementos de referência das requisições via Websocket.
 * Esses elementos serão utilizados no Param1 de cada requisição.
 * @author Melina Minaya
 */
object ResponseObjectReference {
    /**
     * Endpoint Geral de Configuração de Eventos (Ações/Notificações).
     * Envia a lista de endpoint de notificações que desejam ser escutadas.
     *
     * [ActionValues.MESSAGE_STATUS] : retorna [ActionValues.MessageStatusValues]
     *
     * [ActionValues.COMMUNICATION_MODE_CHANGED] : [br.com.autotrac.testnanoclient.consts.ParameterValues.ValuesNetworkTypes]
     *
     * [ActionValues.FILE_OPERATION_STATUS] : [ActionValues.ValuesFileOperationStatusParam1]
     *
     * [ActionValues.IGNITION_STATUS] : [ActionValues.ValuesIgnitionStatusParam1]
     *
     * [ActionValues.NETWORK_CONNECTION_STATUS] : [br.com.autotrac.testnanoclient.consts.ParameterValues.ValuesNetworkTypes] e
     * [br.com.autotrac.testnanoclient.consts.ParameterValues.ValuesConnectionStates]
     *
     * [ActionValues.SYSTEM_RESOURCE_REQ_STATUS] : [ActionValues.ValuesSysResourceReqParam1]
     * e [ActionValues.ValuesSysResourceStatusParam2]
     *
     * [ActionValues.FORM_RECEIVED] e [ActionValues.FORM_DELETED] : [br.com.autotrac.testnanoclient.models.IntegrationForm.code]
     *
     *
     * Exemplo de requisição com Endpoint: {"param1":"NOTIFICATION","param2":[22,7,10,18,21,24,15,9,8,11,5,23,4,3]}
     *
     * @return Resposta do Servidor do tipo [br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse].
     *
     * @param param1 Pode ser [NOTIFICATION], [REQUEST], [PARAMETER],

     * @see br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse
     */
    const val NOTIFICATION = "NOTIFICATION"

    /**
     * Endpoint Geral de Requisição.
     *
     * Exemplo de requisição com Endpoint: {"param1":"REQUEST","param2":"REQ_MESSAGE_COUNT","param3":{"param1":"false","param2":"3"},"param4":0}
     *
     * @return Resposta do Servidor do tipo [br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse].
     * @see br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse
     */
    const val REQUEST = "REQUEST"

    /**
     * Endpoint Geral de Requisição de Parâmetros.
     *
     * Exemplo de requisição com Endpoint: {"param1":"PARAMETER","param2":"SET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S","param3":{"param1":"10"}}
     *
     *  @return Resposta do Servidor do tipo [br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse].
     *  @see br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse
     */
    const val PARAMETER = "PARAMETER"
}

/**
 * Retornos estáticos possíveis para requisições de [ResponseObjectReference.REQUEST]
 * ou [ResponseObjectReference.PARAMETER].
 */
object ApiResponses{
    const val TIME_OUT = "Socket timeout occurred."
    const val BIND_CONFLICT = "Port is already in use by another application."
    const val INTERNAL_ERROR = "Internal server error."
    const val INTERRUPTED_WAITING = "Interrupted while waiting."
    const val ON_ERROR = "Error occurred."
    const val UNAUTHORIZED_REQUEST = "Unauthorized Request."
    const val ENDPOINT_NOT_FOUND = "Endpoint Not Found."
    const val BAD_REQUEST = "Bad Request."
    const val PACKAGE_UNAUTHORIZED = "PackageName not authorized."
    const val MESSAGE_RECEIVED = "Message Received."
    const val OK = "OK"
    const val INVALID_MSG_COMBINATION = "Invalid message subtype and body combination."
    const val PARAMS_INVALID_NOT_FOUND = "Request Params are invalid or not found."
    const val UC_NOT_ENABLE = "UC not enabled. Please enable it and try again."
    const val UC_BAPTIZED_UNABLE_TO_CHANGE_MODE = "UC is baptized and unable to change external device communication type. Please debaptize it and try again."
    const val INVALID_HEADERS = "Invalid headers."
}
