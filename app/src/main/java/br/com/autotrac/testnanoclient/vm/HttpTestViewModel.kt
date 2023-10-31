package br.com.autotrac.testnanoclient.vm

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.autotrac.testnanoclient.NanoHttpClient
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.dataRemote.IntegrationMessage
import br.com.autotrac.testnanoclient.requestObjects.RequestObject
import br.com.autotrac.testnanoclient.handlers.ParseOnMessage
import br.com.autotrac.testnanoclient.handlers.ParseResult
import br.com.autotrac.testnanoclient.logger.AppLogger
import br.com.autotrac.testnanoclient.screens.messageOnPattern
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class HttpTestViewModel: ViewModel() {

    private var gson = Gson()

    private val _reqMessageCount = MutableLiveData<String>()
    val reqMessageCount: MutableLiveData<String> get() = _reqMessageCount

    private val _reqMessageList = MutableLiveData<List<IntegrationMessage>>()
    val reqMessageList: MutableLiveData<List<IntegrationMessage>> get() = _reqMessageList

    private val _responseReq = MutableLiveData<String>()
    val responseRequest: MutableLiveData<String> get() = _responseReq

    private val parseOnMessage: ParseOnMessage = ParseOnMessage()

    private val _isSocketOn = MutableLiveData(false)
    val isSocketOn: MutableLiveData<Boolean> get() = _isSocketOn
    /**
     * Método para retornar contagem de mensagens de acordo com o filtro especificado.
     * No caso abaixo, a requisição é feita para contagem de mensagens não lidas.
     */
    suspend fun messageCountHttp() {

        val reqMessageCountFilter = RequestObject(false, 3, null, null)
        var fetchRequestResponse: String = withContext(Dispatchers.IO) {
            NanoHttpClient.sendGetRequestHttp(
                ApiEndpoints.REQ_MESSAGE_COUNT,
                reqMessageCountFilter
            )
        }

        Log.d("HTTP GET request", "HTTP GET request: $fetchRequestResponse")
        AppLogger.log("HTTP GET request: $fetchRequestResponse")
        if (fetchRequestResponse != "" && fetchRequestResponse != null) {
            // Request successful, process the response
            _isSocketOn.value = true
            val result =
                parseOnMessage.parseMessage(fetchRequestResponse)
            if (result == ParseResult.Ok) {
                val valueResponse = ObservableUtil.transformJsonToInteger(
                    ObservableUtil.getValue(ApiEndpoints.REQ_MESSAGE_COUNT).toString()
                ).toString()
                _reqMessageCount.value = valueResponse
            } else {
                Log.e("ParseOnMessage", "Error on parse")
                AppLogger.log("Error on parse in MessageCount - HttpTestViewModel.kt")
                return
            }
        } else {
            _isSocketOn.value = false
        }
    }

    /**
     * Método que requisita de acordo com o endpoint e filtro específico.
     * Retorna a resposta da requisição Http.
     */
    suspend fun fetchRequest(url: String, requestObject: RequestObject, context: Context) {

        when (url) {
            ApiEndpoints.SEND_MESSAGE -> {
                val messageParseResult = gson.toJson(
                    messageOnPattern(
                        requestObject.param1.toString(),
                        null, null
                    ), IntegrationMessage::class.java
                )
                val newRequestObject: RequestObject = RequestObject(
                    param1 = messageParseResult,
                    param2 = requestObject.param2,
                    param3 = requestObject.param3,
                    param4 = requestObject.param4
                )

                try {
                    val fetchRequestResponse: String = withContext(Dispatchers.IO) {
                        NanoHttpClient.sendGetRequestHttp(
                            url,
                            newRequestObject
                        )
                    }
                    _responseReq.value = fetchRequestResponse
                } catch (e: Exception) {
                    _responseReq.value = "Error: ${e.message}"
                }
            }
            ApiEndpoints.SEND_FILE_MESSAGE -> {
                val messageParseResult = gson.toJson(
                    messageOnPattern(
                        requestObject.param1.toString(),
                        Uri.parse(requestObject.param4.toString()),
                        requestObject.param3.toString()
                    ), IntegrationMessage::class.java
                )
                val messageDbParsed = messageOnPattern(
                    requestObject.param1.toString(),
                    Uri.parse(requestObject.param4.toString()),
                    requestObject.param3.toString()
                )


                try {
                    val fetchRequestResponse: String = withContext(Dispatchers.IO) {
                        NanoHttpClient.sendFileChunksHttp(
                            url,
                            messageDbParsed,
                            8192,
                            Uri.parse(requestObject.param4.toString()),
                            context
                        )
                    }
                    _responseReq.value = fetchRequestResponse
                } catch (e: Exception) {
                    _responseReq.value = "Error: ${e.message}"
                }

            }
            else -> {
                try {
                    val fetchRequestResponse: String = withContext(Dispatchers.IO) {
                        NanoHttpClient.sendGetRequestHttp(
                            url,
                            requestObject
                        )
                    }
                    _responseReq.value = fetchRequestResponse
                } catch (e: Exception) {
                    _responseReq.value = "Error: ${e.message}"
                }
            }
        }

        }


}
