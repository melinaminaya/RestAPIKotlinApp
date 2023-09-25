package com.example.nanoclientkotlin.handlers

import android.util.Log
import com.example.nanoclientkotlin.NanoWebsocketClient
import com.example.nanoclientkotlin.ObservableUtil
import com.example.nanoclientkotlin.consts.ActionValues
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.ParseData
import com.example.nanoclientkotlin.dataRemote.ReceivedRequestResponse
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException

sealed class ParseResult {
    object Ok : ParseResult()
    data class Error(val errorMessage: String) : ParseResult()
}
class ParseOnMessage {
    val gson = Gson()
    companion object {
        const val TAG = "ParseOnMessage"
    }
    // Define a callback interface
    interface NotificationListener {
        fun onNotificationReceived(notification: ReceivedRequestResponse)
    }

    private var notificationListener: NotificationListener? = null

    // Setter for the listener
    fun setNotificationListener(listener: NotificationListener) {
        notificationListener = listener
    }
    fun parseMessage(message:String): ParseResult {
        try {
            val jsonElement = JsonParser.parseString(message)
            if (!jsonElement.isJsonObject) {
                // Exit early if it's not a JSON object
                return ParseResult.Error("Not a JSON object")
            }
            val notification =
                gson.fromJson(message, ReceivedRequestResponse::class.java)
            val param1 = notification.param1 ?:  return ParseResult.Error("param1 is null") // Exit if param1 is null

            when (param1) {
                ConstsCommSvc.NOTIFICATION -> {
                    //TODO: Tratar recebimento de notificação, conforme cliente necessita.
                   val notificationType = notification.param2 ?: return ParseResult.Error("param2 is null") // Exit if param2 is null
                    if(notificationType.toString() == ActionValues.BAPTISM_STATUS_OBSERVABLE) {
//                        notificationListener?.onNotificationReceived(notification)
                        ObservableUtil.attachProperty(ActionValues.BAPTISM_STATUS_OBSERVABLE, notification.param3)
                    }
                    Log.d(Companion.TAG, "Received response relative to notification: $notification")
                    return ParseResult.Ok
                }

                ConstsCommSvc.REQUEST -> {
                    val objectReq = notification.param4 ?: return ParseResult.Error("param4 is null") // Exit if param4 is null
                    when (notification.param2) {
                        ConstsCommSvc.REQ_MESSAGE_LIST -> {
                            val param3Json = notification.param3.toString()
                            val filterList =
                                ParseData.parseMessageList(param3Json)
                            Log.d(Companion.TAG, "$filterList")
                            when (filterList.param2) {
                                true -> ObservableUtil.attachProperty(
                                    ConstsCommSvc.REQ_MESSAGE_LIST_OUTBOX,
                                    objectReq
                                ) // equal fetchOutboxMessages
                                false -> ObservableUtil.attachProperty(
                                    ConstsCommSvc.REQ_MESSAGE_LIST_INBOX,
                                    objectReq
                                )
                            }
                        }

                        ConstsCommSvc.REQ_MESSAGE_COUNT, ConstsCommSvc.REQ_FORM_LIST,
                        ConstsCommSvc.REQ_GET_CHECKLIST, ConstsCommSvc.REQ_CELL_SIGNAL,
                        ConstsCommSvc.REQ_WIFI_SIGNAL, ConstsCommSvc.REQ_GET_CURRENT_DATE,
                        ConstsCommSvc.REQ_GET_MCT_PARAMETERS, ConstsCommSvc.REQ_GET_POSITION_LAST,
                        ConstsCommSvc.REQ_POSITION_HISTORY_COUNT, ConstsCommSvc.REQ_POSITION_HISTORY_LIST,
                        ConstsCommSvc.REQ_RESET_DATABASE, ConstsCommSvc.REQ_MESSAGE_DELETE,
                        ConstsCommSvc.REQ_MESSAGE_SET_AS_READ, ConstsCommSvc.REQ_CONFIG_SERVICE_LOG,
                        ConstsCommSvc.REQ_FILE_OPERATION,
                        -> {
                            ObservableUtil.attachProperty(
                                notification.param2,
                                objectReq
                            )
                        }
                    }
                    Log.d(Companion.TAG, "Received ${notification.param2}: $notification")
                    return ParseResult.Ok
                }

                ConstsCommSvc.PARAMETER -> {
                    val objectReq = notification.param3 ?: return ParseResult.Error("param3 is null")
                    when (notification.param2) {
                        in ConstsCommSvc.parametersList.filter { it.startsWith("GET") } -> {
                            ObservableUtil.attachProperty(
                                notification.param2!!,
                                objectReq
                            )
                        }

                        in ConstsCommSvc.parametersList.filter { it.startsWith("SET") } -> {
                            //TODO: TO BE IMPLEMENTED

                        }
                    }
                    Log.d(Companion.TAG, "Received ${notification.param2}: $notification")
                    return ParseResult.Ok
                }

                ConstsCommSvc.SEND_MESSAGE -> {
                    NanoWebsocketClient.currentMsgCode = notification.param2
                    ObservableUtil.attachProperty(ConstsCommSvc.SEND_MESSAGE, notification.param2)
                    Log.d(Companion.TAG, "Received Sent Information: $notification")
                    return ParseResult.Ok
                }
                ConstsCommSvc.SEND_FILE_MESSAGE -> {
                    NanoWebsocketClient.currentMsgCode = notification.param2
                    val objectReq = notification.param3 ?: return ParseResult.Error("param3 is null")
                    ObservableUtil.attachProperty(
                        notification.param2!!,
                        objectReq
                    )
                    Log.d(Companion.TAG, "Received Sent Confirmation: $notification")
                    return ParseResult.Ok
                }
                "response" -> {
                    Log.d(Companion.TAG, "Received message: $message")
                    return ParseResult.Ok
                }

                else -> {
                    // Handle case when notification.param1 is null
                    Log.d(Companion.TAG, "Received message with unknown param1: $message")
                    return ParseResult.Error("Unknown param1 value: $param1")
                }
            }

        } catch (e: JsonSyntaxException) {
            //e.printStackTrace()
            Log.d(Companion.TAG, "Received JsonException: $message")
            return ParseResult.Error("JsonSyntaxException")
        }

    }


}