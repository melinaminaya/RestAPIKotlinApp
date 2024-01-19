package br.com.autotrac.testnanoclient.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.models.ParameterModel
import br.com.autotrac.testnanoclient.handlers.MessageSenderAccess
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import kotlinx.coroutines.delay


open class MctParamsViewModel: ViewModel() {

    private val _currentDate = MutableLiveData<List<ParameterModel>>()
    val mctParams: MutableLiveData<List<ParameterModel>> get() = _currentDate
    private val senderAccess = MessageSenderAccess()
    private var gson = Gson()
    private val mapper = ObjectMapper()



    suspend fun fetchData() {
        senderAccess.sendRequest(
            ApiEndpoints.REQ_GET_MCT_PARAMETERS,
            null, null, null, null)

        val fetchedMessages: List<ParameterModel> = fetchDataFromDataSource()
        _currentDate.value = fetchedMessages
    }
//TODO: Not deserializing into Int, yet deserializing into Double
    private suspend fun fetchDataFromDataSource(): List<ParameterModel> {
        delay(1000)
        val valueOnLaunched = ObservableUtil.getValue(ApiEndpoints.REQ_GET_MCT_PARAMETERS)
        val jsonOnLaunched = gson.toJson(valueOnLaunched)

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
        return mapper.readValue(
            jsonOnLaunched,
            object : TypeReference<MutableList<ParameterModel>>() {})

    }

}

