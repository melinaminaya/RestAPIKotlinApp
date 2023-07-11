package com.example.nanoclientkotlin.vm

import android.R.attr
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nanoclientkotlin.MessageSenderAccess
import com.example.nanoclientkotlin.ObservableUtil
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.CheckList
import com.example.nanoclientkotlin.dataRemote.FormList
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import java.nio.charset.StandardCharsets


open class CheckListViewModel: ViewModel() {

    private val _checkList = MutableLiveData<List<CheckList>>()
    val checkList: MutableLiveData<List<CheckList>> get() = _checkList
    private val senderAccess = MessageSenderAccess()
    private var gson = Gson()
    private val mapper = ObjectMapper()



    suspend fun fetchCheckList() {
        senderAccess.sendRequest(ConstsCommSvc.REQ_GET_CHECKLIST, null, null, null)

        val fetchedMessages: List<CheckList>? = fetchDataFromDataSource()
        _checkList.value = fetchedMessages
    }

    private suspend fun fetchDataFromDataSource(): List<CheckList>? {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(ConstsCommSvc.REQ_GET_CHECKLIST)
        if(valueOnLaunched != null) {
            val jsonOnLaunched = gson.toJson(valueOnLaunched)

            mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

            return mapper.readValue(
                jsonOnLaunched,
                object : TypeReference<MutableList<CheckList>>() {})
        }else{
            return null
        }
    }

}

