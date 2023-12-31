package com.example.nanoclientkotlin.vm

import android.R.attr
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nanoclientkotlin.MessageSenderAccess
import com.example.nanoclientkotlin.ObservableUtil
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.CheckList
import com.example.nanoclientkotlin.dataRemote.FormList
import com.example.nanoclientkotlin.dataRemote.LastPosition
import com.example.nanoclientkotlin.dataRemote.PositionHistory
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import java.nio.charset.StandardCharsets


open class LastPositionViewModel: ViewModel() {

    private val _lastPosition = MutableLiveData<LastPosition>()
    val lastPosition: MutableLiveData<LastPosition> get() = _lastPosition

    private val _positionHistoryCount = MutableLiveData<String>()
    val positionHistoryCount: MutableLiveData<String> get() = _positionHistoryCount

    private val _positionHistoryList = MutableLiveData<List<PositionHistory>>()
    val positionHistoryList: MutableLiveData<List<PositionHistory>> get() = _positionHistoryList

    private val senderAccess = MessageSenderAccess()
    private var gson = Gson()
    private val mapper = ObjectMapper()

    /**
     * @param param1 : GPS -> value 0, MCT -> 1
     */

    suspend fun fetchPositionLast() {

        senderAccess.sendRequest(ConstsCommSvc.REQ_GET_POSITION_LAST,0 , null, null, null)

        val fetchedMessages: LastPosition? = fetchDataFromDataSource()
        _lastPosition.value = fetchedMessages
    }

    private suspend fun fetchDataFromDataSource(): LastPosition? {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(ConstsCommSvc.REQ_GET_POSITION_LAST)
//        val jsonOnLaunched =  gson.toJson(valueOnLaunched)
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true)

        val jsonOnLaunched = mapper.writeValueAsString(valueOnLaunched)
        return mapper.readValue(
            jsonOnLaunched,
            LastPosition::class.java)

    }

    /**
     * Only for PrimeMobile:
     * MESSAGE_DUPLICATE	-5514
        MESSAGE_TOO_BIG	-5531
        NONE	0
        NOT_PROCESSED	5
        NOT_READ	3
        NOT_READ_OUT_OF_BAND	7
        OUT_OF_BAND_FILE_NOT_FOUND	-5561
        READ	4
        SENT	2
        SERIAL_MESSAGE_CELL_NET_UNAVAILABLE	1001
        SERIAL_MESSAGE_TOO_BIG_WAITING	1000
        TO_SEND	1
        TRANSMITTED	6
        TRANSMITTING	8
        UNKNOWN_ERROR	-5599
     */

    suspend fun fetchPositionHistoryCount() {

        senderAccess.sendRequest(ConstsCommSvc.REQ_POSITION_HISTORY_COUNT,2 ,
            null,
            null,
            null)

        val fetchedMessages: String = fetchDataFromDataSourceCount()
        _positionHistoryCount.value = fetchedMessages
    }

    private suspend fun fetchDataFromDataSourceCount(): String {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(ConstsCommSvc.REQ_POSITION_HISTORY_COUNT)
//        val jsonOnLaunched =  gson.toJson(valueOnLaunched)
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true)

        val jsonOnLaunched = mapper.writeValueAsString(valueOnLaunched)
        return mapper.readValue(
            jsonOnLaunched,
            String::class.java)

    }

    /**
     * @param param1: posCode - position code to be returned. If it is 0, it will be ignored.
     * If different from 0, other filters will be ignored and only message with the specific code will be returned.
     *
     *@param param2: msgStatusNum - integer status of message to be filtered. MsgStatusNum as applied above.
         This parameter will only be considered if posCode is == 0.
     */
    suspend fun fetchPositionHistoryList() {

        senderAccess.sendRequest(ConstsCommSvc.REQ_POSITION_HISTORY_LIST,0 ,
            4, null, null)

        val fetchedMessages: List<PositionHistory>? = fetchDataFromDataSourceList()
        _positionHistoryList.value = fetchedMessages
    }

    private suspend fun fetchDataFromDataSourceList(): List<PositionHistory>? {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(ConstsCommSvc.REQ_POSITION_HISTORY_LIST)
//        val jsonOnLaunched =  gson.toJson(valueOnLaunched)
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true)

        val jsonOnLaunched = mapper.writeValueAsString(valueOnLaunched)
        return mapper.readValue(
            jsonOnLaunched,
            object : TypeReference<MutableList<PositionHistory>>() {})

    }

}

