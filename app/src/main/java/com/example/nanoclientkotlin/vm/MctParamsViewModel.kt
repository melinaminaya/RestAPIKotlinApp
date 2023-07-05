package com.example.nanoclientkotlin.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nanoclientkotlin.MessageSenderAccess
import com.example.nanoclientkotlin.ObservableUtil
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.ParameterModel
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay


open class MctParamsViewModel: ViewModel() {

    private val _currentDate = MutableLiveData<List<ParameterModel>>()
    val mctParams: MutableLiveData<List<ParameterModel>> get() = _currentDate
    private val senderAccess = MessageSenderAccess()
    private var gson = Gson()
    private val mapper = ObjectMapper()



    suspend fun fetchData() {
        senderAccess.sendRequest(ConstsCommSvc.REQ_GET_MCT_PARAMETERS, null, null, null)

        val fetchedMessages: List<ParameterModel> = fetchDataFromDataSource()
        _currentDate.value = fetchedMessages
    }

    private suspend fun fetchDataFromDataSource(): List<ParameterModel> {
        delay(1000)
        val valueOnLaunched = ObservableUtil.getValue(ConstsCommSvc.REQ_GET_MCT_PARAMETERS)
        val jsonOnLaunched = gson.toJson(valueOnLaunched)

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
        return mapper.readValue(
            jsonOnLaunched,
            object : TypeReference<MutableList<ParameterModel>>() {})

    }

}

