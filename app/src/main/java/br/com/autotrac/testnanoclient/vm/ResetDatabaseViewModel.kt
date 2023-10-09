package br.com.autotrac.testnanoclient.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.consts.ApiConstEndpoints
import br.com.autotrac.testnanoclient.handlers.MessageSenderAccess
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import kotlinx.coroutines.delay


open class ResetDatabaseViewModel: ViewModel() {

    private val _resetDatabase = MutableLiveData<String>()
    val resetDatabaseResponse: MutableLiveData<String> get() = _resetDatabase
    private val senderAccess = MessageSenderAccess()
    private var gson = Gson()
    private val mapper = ObjectMapper()



   suspend fun fetchResetDb() {
        senderAccess.sendRequest(
            ApiConstEndpoints.REQ_RESET_DATABASE, null,
            null, null, null)

        val fetchedMessages: String = fetchDataFromDataSource()
        _resetDatabase.value = fetchedMessages
    }

    private suspend fun fetchDataFromDataSource(): String {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(ApiConstEndpoints.REQ_RESET_DATABASE)
        val jsonOnLaunched =  gson.toJson(valueOnLaunched)

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

        return mapper.readValue(
            jsonOnLaunched,
            object : TypeReference<String>() {})

    }

}
