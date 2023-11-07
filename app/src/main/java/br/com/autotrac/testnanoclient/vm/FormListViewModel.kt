package br.com.autotrac.testnanoclient.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.consts.ActionValues
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.models.IntegrationForm
import br.com.autotrac.testnanoclient.handlers.MessageSenderAccess
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale

open class FormListViewModel (
): ViewModel() {

    private val _formList = MutableLiveData<List<IntegrationForm>>()
    val formList: MutableLiveData<List<IntegrationForm>> get() = _formList
    private val senderAccess = MessageSenderAccess()
    private val gson = Gson()
    private val mapper = ObjectMapper()

    suspend fun fetchMessages() {
        senderAccess.sendRequest(ApiEndpoints.REQ_FORM_LIST, false, 0,
            ActionValues.FormTypeValues.FIXED_TO_MOBILE_TXT, null)
//        sendMessageAndWait("messageList")
        // Replace with your logic to fetch messages from a data source
        val fetchedMessages: List<IntegrationForm>? = fetchDataFromDataSource()
        _formList.value = fetchedMessages
    }
    private suspend fun fetchDataFromDataSource(): List<IntegrationForm>? {
        delay(1000)
        val valueOnLaunched = ObservableUtil.getValue(ApiEndpoints.REQ_FORM_LIST)
        return if (valueOnLaunched != null  && valueOnLaunched != "[]") {

            val jsonOnLaunched = gson.toJson(valueOnLaunched)
            val dateFormat = SimpleDateFormat("MMM d, yyyy HH:mm:ss", Locale.ENGLISH)
            mapper.dateFormat = dateFormat
            mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)


            mapper.readValue(jsonOnLaunched,
                object : TypeReference<MutableList<IntegrationForm>>() {})

        }else{
            null
        }
    }
}