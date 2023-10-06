package br.com.autotrac.testnanoclient.dataRemote

/**
 * O SendObject é o objeto que é enviado para o servidor.
 * @param param1 refere-se ao endpoint.
 * @param param2 refere-se ao objeto a ser enviado no formato de [RequestObject], que se encontra encapsulado em formato Gson.
 */
data class SendObject(val param1: String?, val param2: Any?)

/**
 * O ReceivedRequestResponse refere-se ao objeto que é recebido pelo WebSocket ou requisições HTTP.
 *
 * @param param1 Referencia aos Endpoints Gerais de [br.com.autotrac.testnanoclient.consts.ApiConstEndpoints.REQUEST],
 * [br.com.autotrac.testnanoclient.consts.ApiConstEndpoints.NOTIFICATION] e
 * [br.com.autotrac.testnanoclient.consts.ApiConstEndpoints.PARAMETER].
 * @param param2 Referencia aos Endpoints Específicos
 * @param param3 Referencia o filtro da mensagem ou requisição solicitada
 * @param param4 Valor, resposta do servidor.
 */
data class ReceivedRequestResponse(
    val param1: String?,
    val param2: String?,
    val param3: Any?,
    val param4: Any?,
)

/**
 * Objeto a ser enviado em [br.com.autotrac.testnanoclient.consts.ApiConstEndpoints.REQUEST] e
 * [br.com.autotrac.testnanoclient.consts.ApiConstEndpoints.PARAMETER] contendo os parâmetros
 * necessários para cada requisição ao servidor.
 * Ao enviar via WebSocket, o objeto somente é convertido para gson e encapsulado dentro do [SendObject].
 * Por meio de requisição HTTP, o objeto é distribuído nas 4 queries enviadas ao servidor.
 */
data class RequestObject(val param1: Any?, val param2: Any?, val param3: Any?, val param4: Any?)

/**
 * Objeto a ser enviado quando envia-se um arquivo por meio de [br.com.autotrac.testnanoclient.consts.ApiConstEndpoints.SEND_FILE_MESSAGE].
 * Também utilizado no envio de mensagens por meio de [br.com.autotrac.testnanoclient.consts.ApiConstEndpoints.SEND_MESSAGE] via WebSocket.
 * Via HTTP o [br.com.autotrac.testnanoclient.consts.ApiConstEndpoints.SEND_MESSAGE] é enviado via requisição normal, porém mensagens longas
 * e/ou com arquivos serão enviadas por meio de WebSocket e portanto o objeto [ChunkObject] também será utilizado.
 * Por fim este objeto será encapsulado como param2 no [SendObject].
 */
data class ChunkObject(
    val message: String,
    val totalChunks: Int,
    val chunkNumber: Int,
    val data: String, // Use String to represent the chunk data
)
