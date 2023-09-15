package com.example.nanoclientkotlin.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nanoclientkotlin.ObservableUtil
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.FormList
import com.example.nanoclientkotlin.handlers.MessageSenderAccess
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson

open class FormListViewModel (
//    private val getGamesUseCase: GetGamesUseCase,
//    private val getGameByIdUseCase: GetGameByIdUseCase
): ViewModel() {

    private val _formList = MutableLiveData<List<FormList>>()
    val formList: MutableLiveData<List<FormList>> get() = _formList
    private val senderAccess = MessageSenderAccess()
    private val gson = Gson()
    private val mapper = ObjectMapper()

    fun fetchMessages() {
        senderAccess.sendRequest(ConstsCommSvc.REQ_FORM_LIST, false, 0, 1, null)
//        sendMessageAndWait("messageList")
        // Replace with your logic to fetch messages from a data source
        val fetchedMessages: List<FormList>? = fetchDataFromDataSource()
        _formList.value = fetchedMessages
    }
    private fun fetchDataFromDataSource(): List<FormList>? {
        val valueOnLaunched = ObservableUtil.getValue("formList")
        if (valueOnLaunched != null) {
            val jsonOnLaunched = gson.toJson(valueOnLaunched)
            mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//            .setDateFormat(StdDateFormat())
//            .registerKotlinModule()
//
//        val simpleModule = SimpleModule()
//            .addDeserializer(Date::class.java, CustomDateDeserializer())
//
//        mapper.registerModule(simpleModule)

            return mapper.readValue(
                jsonOnLaunched,
                object : TypeReference<MutableList<FormList>>() {})
        }else{
            return null
        }
    }
}