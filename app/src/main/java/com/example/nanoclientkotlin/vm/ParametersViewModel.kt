package com.example.nanoclientkotlin.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nanoclientkotlin.ObservableUtil
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.ReceivedRequestResponse
import com.example.nanoclientkotlin.handlers.MessageSenderAccess
import com.example.nanoclientkotlin.handlers.ParseOnMessage
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import kotlinx.coroutines.delay

class ParametersViewModel: ViewModel(), ParseOnMessage.NotificationListener {

    private val senderAccess = MessageSenderAccess()
    private var gson = Gson()
    private val mapper = ObjectMapper()


    private val parameterListGet = ConstsCommSvc.parametersList.filter { it.startsWith("GET") }
    private val parameterLiveDataMap = HashMap<String, MutableLiveData<String>>()

    private val _isBaptizedValue = MutableLiveData<String>("") // Initialize with default value
    val isBaptizedValue: MutableLiveData<String> = _isBaptizedValue
    private val parseOnMessage = ParseOnMessage()

    init {
        // Create MutableLiveData objects for each parameter and add them to the map
        for (parameter in parameterListGet) {
            parameterLiveDataMap[parameter] = MutableLiveData()
        }
        parseOnMessage.setNotificationListener(this)
    }

    fun getParameterLiveData(parameter: String): MutableLiveData<String>? {
        return parameterLiveDataMap[parameter]
    }

    suspend fun fetchParameters() {
        for (parameter in parameterListGet) {
            senderAccess.sendRequest(parameter, null, null, null, null)
            val fetchedMessages: String? = fetchDataFromDataSource(parameter)

            val parameterLiveData = parameterLiveDataMap[parameter]
            parameterLiveData?.value = fetchedMessages

        }
    }

    private suspend fun fetchDataFromDataSource(param: String): String? {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(param)
        return if (valueOnLaunched != null) {
            val jsonOnLaunched = gson.toJson(valueOnLaunched)

            mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

            mapper.readValue(
                jsonOnLaunched,
                object : TypeReference<String>() {})
        } else {
            null
        }
    }

    fun setParam(param: String, value: String) {
        // Replace with your logic to delete the message from a data source
        senderAccess.sendRequest(param, value, null, null, null)
        // Remove the message from the list of messages
        val parameterLiveData = parameterLiveDataMap[param]
        parameterLiveData?.value = value

    }
//    fun updateIsBaptizedValue(newValue: String) {
//        _isBaptizedValue.value = newValue
//    }

    override fun onNotificationReceived(notification: ReceivedRequestResponse) {
        _isBaptizedValue.value = notification.param3.toString()
        Log.d("ONNOTIFICATION", "onNotificationReceived BAPTISM_STATUS: ${isBaptizedValue.value}")
    }
}
