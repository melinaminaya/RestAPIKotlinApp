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
    private val senderAccess = MessageSenderAccess()
    private var gson = Gson()
    private val mapper = ObjectMapper()

    /**
     * @param param1 : GPS -> value 0, MCT -> 1
     */

    suspend fun fetchPositionLast() {

        senderAccess.sendRequest(ConstsCommSvc.REQ_GET_POSITION_LAST,0 , null, null)

        val fetchedMessages: LastPosition = fetchDataFromDataSource()
        _lastPosition.value = fetchedMessages
    }

    private suspend fun fetchDataFromDataSource(): LastPosition {
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

}

