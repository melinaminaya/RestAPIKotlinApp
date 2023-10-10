package br.com.autotrac.testnanoclient.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.consts.ActionValues
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.dataRemote.LastPosition
import br.com.autotrac.testnanoclient.dataRemote.PositionHistory
import br.com.autotrac.testnanoclient.handlers.MessageSenderAccess
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.google.gson.Gson
import kotlinx.coroutines.delay


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

        senderAccess.sendRequest(ApiEndpoints.REQ_GET_POSITION_LAST,
            ActionValues.PositionSourceType.GPS , null, null, null)

        val fetchedMessages: LastPosition? = fetchDataFromDataSource()
        _lastPosition.value = fetchedMessages
    }

    private suspend fun fetchDataFromDataSource(): LastPosition? {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(ApiEndpoints.REQ_GET_POSITION_LAST)
//        val jsonOnLaunched =  gson.toJson(valueOnLaunched)
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true)

        val jsonOnLaunched = mapper.writeValueAsString(valueOnLaunched)
        return mapper.readValue(
            jsonOnLaunched,
            LastPosition::class.java)

    }

    /**
     * Somente para o PrimeMobile:
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

        senderAccess.sendRequest(ApiEndpoints.REQ_POSITION_HISTORY_COUNT,2 ,
            null,
            null,
            null)

        val fetchedMessages: String = fetchDataFromDataSourceCount()
        _positionHistoryCount.value = fetchedMessages
    }

    private suspend fun fetchDataFromDataSourceCount(): String {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(ApiEndpoints.REQ_POSITION_HISTORY_COUNT)
//        val jsonOnLaunched =  gson.toJson(valueOnLaunched)
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true)

        val jsonOnLaunched = mapper.writeValueAsString(valueOnLaunched)
        return mapper.readValue(
            jsonOnLaunched,
            String::class.java)

    }

    /**
     * @param1: posCode - é o código da posição a ser retornada.
     * Se este parâmetro for igual a 0, ele será ignorado.
     * Se for diferente de 0, os demais filtros serão ignorados e somente a mensagem com o código especificado será retornada.
     *
     *@param2: msgStatusNum - é número do status a ser filtrado.
     *@see ActionValues.MessageStatusValues
     * Este parâmetro só é considerado se posCode for igual a 0.
     */
    suspend fun fetchPositionHistoryList() {

        senderAccess.sendRequest(ApiEndpoints.REQ_POSITION_HISTORY_LIST,0 ,
            4, null, null)

        val fetchedMessages: List<PositionHistory>? = fetchDataFromDataSourceList()
        _positionHistoryList.value = fetchedMessages
    }

    private suspend fun fetchDataFromDataSourceList(): List<PositionHistory>? {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(ApiEndpoints.REQ_POSITION_HISTORY_LIST)
//        val jsonOnLaunched =  gson.toJson(valueOnLaunched)
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true)

        val jsonOnLaunched = mapper.writeValueAsString(valueOnLaunched)
        return mapper.readValue(
            jsonOnLaunched,
            object : TypeReference<MutableList<PositionHistory>>() {})

    }

}

