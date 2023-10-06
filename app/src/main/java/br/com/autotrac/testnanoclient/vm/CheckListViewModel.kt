package br.com.autotrac.testnanoclient.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.consts.ApiConstEndpoints
import br.com.autotrac.testnanoclient.dataRemote.CheckList
import br.com.autotrac.testnanoclient.handlers.MessageSenderAccess
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import kotlinx.coroutines.delay


open class CheckListViewModel: ViewModel() {

    private val _checkList = MutableLiveData<List<CheckList>>()
    val checkList: MutableLiveData<List<CheckList>> get() = _checkList
    private val _cellSignal = MutableLiveData<String>()
    val cellSignal: MutableLiveData<String> get() = _cellSignal
    private val _wifiSignal = MutableLiveData<String>()
    val wifiSignal: MutableLiveData<String> get() = _wifiSignal

    private val senderAccess = MessageSenderAccess()
    private var gson = Gson()
    private val mapper = ObjectMapper()



    suspend fun fetchCheckList() {
        senderAccess.sendRequest(ApiConstEndpoints.REQ_GET_CHECKLIST, null, null, null, null)

        val fetchedMessages: List<CheckList>? = fetchDataFromDataSource()
        _checkList.value = fetchedMessages
    }

    private suspend fun fetchDataFromDataSource(): List<CheckList>? {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(ApiConstEndpoints.REQ_GET_CHECKLIST)
        return if(valueOnLaunched != null) {
            val jsonOnLaunched = gson.toJson(valueOnLaunched)

            mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

            mapper.readValue(
                jsonOnLaunched,
                object : TypeReference<MutableList<CheckList>>() {})
        }else{
            null
        }
    }

    fun sendConfigServiceLog(enable: Boolean, maxFileCount: Int, maxFileSize: Long){
        senderAccess.sendRequest(
            ApiConstEndpoints.REQ_CONFIG_SERVICE_LOG,
            enable, maxFileCount, maxFileSize, null)
    }
    fun sendFileOperation(files: Int, options: Int, destination: String, timeoutMS: Int){
        senderAccess.sendRequest(ApiConstEndpoints.REQ_CONFIG_SERVICE_LOG,
            files, options, destination, timeoutMS)
    }

    /**
     * Example calls for fetching CellSignal and WifiSignal values from the Requests
     */
    suspend fun fetchCellSignal(){
        senderAccess.sendRequest(ApiConstEndpoints.REQ_CELL_SIGNAL, null, null, null, null)

        val fetchedMessages: String = fetchDataFromDataSourceCellSignal()
        _cellSignal.value = fetchedMessages
    }
    private suspend fun fetchDataFromDataSourceCellSignal(): String {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(ApiConstEndpoints.REQ_CELL_SIGNAL)

        val jsonOnLaunched = gson.toJson(valueOnLaunched)

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

        return mapper.readValue(
            jsonOnLaunched,
            object : TypeReference<String>() {})
    }
}

