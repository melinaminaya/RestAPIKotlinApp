package br.com.autotrac.testnanoclient.handlers

import android.util.Log
import br.com.autotrac.testnanoclient.ApiObservableConsts
import br.com.autotrac.testnanoclient.NanoWebsocketClient
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.consts.ActionValues
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.consts.ResponseObjectReference
import br.com.autotrac.testnanoclient.logger.AppLogger
import br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException

/**
 * Recebimento das requisições e distribuição para os cada Handler específico.
 * Distribui por Endpoint.
 * @author Melina Minaya
 */

sealed class ParseResult {
    object Ok : ParseResult()
    data class Error(val errorMessage: String) : ParseResult()
}

class ParseOnMessage {
    val gson = Gson()

    companion object {
        const val TAG = "ParseOnMessage"
    }

    fun parseMessage(message: String): ParseResult {
        try {
            val jsonElement = JsonParser.parseString(message)
            if (!jsonElement.isJsonObject) {
                // Exit early if it's not a JSON object
                return ParseResult.Error("Not a JSON object")
            }
            val notification =
                gson.fromJson(message, ReceivedRequestResponse::class.java)
            val param1 = notification.param1
                ?: return ParseResult.Error("param1 is null") // Exit if param1 is null

            when (param1) {
                ResponseObjectReference.NOTIFICATION -> {
                    //TODO: Tratar recebimento de notificação, conforme cliente necessita.
                    val notificationType = notification.param2
                        ?: return ParseResult.Error("param2 is null") // Exit if param2 is null
                    when (notificationType.toString()) {
                        ActionValues.BAPTISM_STATUS.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.BAPTISM_STATUS,
                                notification.param3
                            )
                        }

                        ActionValues.MESSAGE_STATUS.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.MESSAGE_STATUS,
                                listOf(notification.param3, notification.param4)
                            )
                        }

                        ActionValues.SATELLITE_SIGNAL_CHANGED.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.SATELLITE_SIGNAL_CHANGED,
                                notification.param3
                            )
                        }

                        ActionValues.FORM_RECEIVED.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.FORM_RECEIVED,
                                notification.param3
                            )
                        }

                        ActionValues.FORM_DELETED.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.FORM_DELETED,
                                notification.param3
                            )
                        }

                        ActionValues.SIGN_ON_STATUS.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.SIGN_ON_STATUS,
                                notification.param3
                            )
                        }

                        ActionValues.READY_TO_UPDATE_SOFTWARE.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.READY_TO_UPDATE_SOFTWARE,
                                notification.param3
                            )
                        }

                        ActionValues.NETWORK_CONNECTION_STATUS.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.NETWORK_CONNECTION_STATUS,
                                notification.param3
                            )
                        }

                        ActionValues.COMMUNICATION_MODE_CHANGED.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.COMMUNICATION_MODE_CHANGED,
                                notification.param3
                            )
                        }

                        ActionValues.MCT_M0_M9_PARAMS_UPDATED.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.MCT_M0_M9_PARAMS_UPDATED,
                                notification.param3
                            )
                        }

                        ActionValues.DATE_TIME_CHANGED.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.DATE_TIME_CHANGED,
                                notification.param3
                            )
                        }

                        ActionValues.IGNITION_STATUS.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.IGNITION_STATUS,
                                notification.param3
                            )
                        }

                        ActionValues.SYSTEM_RESOURCE_REQ_STATUS.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.SYSTEM_RESOURCE_REQ_STATUS,
                                notification.param3
                            )
                        }

                        ActionValues.FILE_OPERATION_STATUS.toString() -> {
                            ObservableUtil.attachProperty(
                                ApiObservableConsts.FILE_OPERATION_STATUS,
                                notification.param3
                            )
                        }
                    }

                    Log.d(Companion.TAG, "Received NOTIFICATION: $notification")
                    AppLogger.log("Received NOTIFICATION: $notification")
                    return ParseResult.Ok
                }

                ResponseObjectReference.REQUEST -> {
                    val objectReq = notification.param4
                        ?: return ParseResult.Error("param4 is null") // Exit if param4 is null
                    when (notification.param2) {
                        ApiEndpoints.REQ_MESSAGE_LIST -> {
                            val param3Json = notification.param3.toString()
                            val filterList =
                                ParseData.parseMessageList(param3Json)
                            Log.d(Companion.TAG, "$filterList")
                            AppLogger.log("FilterList: $filterList")
                            when (filterList.param2) {
                                true -> ObservableUtil.attachProperty(
                                    ApiEndpoints.REQ_MESSAGE_LIST_OUTBOX,
                                    objectReq
                                ) // equal fetchOutboxMessages
                                false -> ObservableUtil.attachProperty(
                                    ApiEndpoints.REQ_MESSAGE_LIST_INBOX,
                                    objectReq
                                )
                            }
                        }

                        ApiEndpoints.REQ_MESSAGE_COUNT, ApiEndpoints.REQ_FORM_LIST,
                        ApiEndpoints.REQ_GET_CHECKLIST, ApiEndpoints.REQ_CELL_SIGNAL,
                        ApiEndpoints.REQ_WIFI_SIGNAL, ApiEndpoints.REQ_GET_CURRENT_DATE,
                        ApiEndpoints.REQ_GET_MCT_PARAMETERS, ApiEndpoints.REQ_GET_POSITION_LAST,
                        ApiEndpoints.REQ_POSITION_HISTORY_COUNT, ApiEndpoints.REQ_POSITION_HISTORY_LIST,
                        ApiEndpoints.REQ_RESET_DATABASE, ApiEndpoints.REQ_MESSAGE_DELETE,
                        ApiEndpoints.REQ_MESSAGE_SET_AS_READ, ApiEndpoints.REQ_CONFIG_SERVICE_LOG,
                        ApiEndpoints.REQ_FILE_OPERATION,
                        -> {
                            ObservableUtil.attachProperty(
                                notification.param2,
                                objectReq
                            )
                        }
                    }
                    Log.d(Companion.TAG, "Received ${notification.param2}: $notification")
                    AppLogger.log("Received ${notification.param2}: $notification")
                    return ParseResult.Ok
                }

                ResponseObjectReference.PARAMETER -> {
                    val objectReq =
                        notification.param3 ?: return ParseResult.Error("param3 is null")
                    when (notification.param2) {
                        in EndpointsLists.parametersList.filter { it.startsWith("GET") } -> {
                            ObservableUtil.attachProperty(
                                notification.param2!!,
                                objectReq
                            )
                        }

                        in EndpointsLists.parametersList.filter { it.startsWith("SET") } -> {
                            ObservableUtil.attachProperty(
                                notification.param2!!,
                                objectReq
                            )
                        }
                    }
                    Log.d(Companion.TAG, "Received ${notification.param2}: $notification")
                    AppLogger.log("Received ${notification.param2}: $notification")
                    return ParseResult.Ok
                }

                ApiEndpoints.SEND_MESSAGE -> {
                    NanoWebsocketClient.currentMsgCode = notification.param2
                    ObservableUtil.attachProperty(ApiEndpoints.SEND_MESSAGE, notification.param2)
                    Log.d(Companion.TAG, "Received Sent Information: $notification")
                    AppLogger.log("Received Sent Information: $notification")
                    return ParseResult.Ok
                }

                ApiEndpoints.SEND_FILE_MESSAGE -> {
                    NanoWebsocketClient.currentMsgCode = notification.param2
                    val objectReq =
                        notification.param3 ?: return ParseResult.Error("param3 is null")
                    ObservableUtil.attachProperty(
                        notification.param2!!,
                        objectReq
                    )
                    ObservableUtil.attachProperty(
                        ApiEndpoints.SEND_FILE_MESSAGE,
                        notification.param2
                    )
                    Log.d(Companion.TAG, "Received Sent Confirmation: $notification")
                    AppLogger.log("Received Sent Confirmation: $notification")
                    return ParseResult.Ok
                }

                "response" -> {
                    Log.d(Companion.TAG, "Received message: $message")
                    AppLogger.log("Received message: $message")
                    return ParseResult.Ok
                }

                "" -> {
                    Log.d(Companion.TAG, "Received message with null param1: $message")
                    AppLogger.log("Received message with null param1: $message")
//                    return ParseResult.Error("Unknown null value: $param1")
                    return ParseResult.Ok
                }

                else -> {
                    // Handle case when notification.param1 is null
                    Log.d(Companion.TAG, "Received message with unknown param1: $message")
                    AppLogger.log("Received message with unknown param1: $message")
                    return ParseResult.Error("Unknown param1 value: $param1")
                }
            }

        } catch (e: JsonSyntaxException) {
            //e.printStackTrace()
            Log.d(Companion.TAG, "Received JsonException: $message")
            AppLogger.log("Received JsonException: $message")
            return ParseResult.Error("JsonSyntaxException")
        }

    }


}