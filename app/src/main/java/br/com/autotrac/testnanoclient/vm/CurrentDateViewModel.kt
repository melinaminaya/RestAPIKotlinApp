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
import java.text.SimpleDateFormat
import java.util.Locale


open class CurrentDateViewModel: ViewModel() {

    private val _currentDate = MutableLiveData<String>()
    val currentDate: MutableLiveData<String> get() = _currentDate
    private val senderAccess = MessageSenderAccess()
    private var gson = Gson()
    private val mapper = ObjectMapper()



    suspend fun fetchCurrentDate() {
        senderAccess.sendRequest(ApiConstEndpoints.REQ_GET_CURRENT_DATE, null,
            null, null, null)

        val fetchedMessages: String = fetchDataFromDataSource()
        _currentDate.value = fetchedMessages
    }

    private suspend fun fetchDataFromDataSource(): String {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(ApiConstEndpoints.REQ_GET_CURRENT_DATE)
        val jsonOnLaunched =  gson.toJson(valueOnLaunched)
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val date = dateFormat.parse(jsonOnLaunched.trim('"'))

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

        return mapper.readValue(
            jsonOnLaunched,
            object : TypeReference<String>() {})

    }

}

