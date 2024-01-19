package br.com.autotrac.testnanoclient.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.ObservableUtil.addPropertyChangeListener
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.consts.ApiResponses
import br.com.autotrac.testnanoclient.handlers.EndpointsLists
import br.com.autotrac.testnanoclient.handlers.MessageSenderAccess
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay
import java.beans.PropertyChangeListener

class ParametersViewModel: ViewModel() {

    private val senderAccess = MessageSenderAccess()
    private var gson = Gson()
    private val mapper = ObjectMapper()


    private val parameterListGet = EndpointsLists.parametersList.filter { it.startsWith("GET") }
    private val parameterLiveDataMap = HashMap<String, MutableLiveData<String>>()

    init {
        // Create MutableLiveData objects for each parameter and add them to the map
        for (parameter in parameterListGet) {
            parameterLiveDataMap[parameter] = MutableLiveData()
        }
    }

    fun getParameterLiveData(parameter: String): MutableLiveData<String>? {
        return parameterLiveDataMap[parameter]
    }

    suspend fun fetchParameters() {
        for (parameter in parameterListGet) {
            senderAccess.sendRequest(parameter, null, null, null, null)
            val fetchedMessages: String? = fetchDataFromDataSource(parameter)

            val parameterLiveData = parameterLiveDataMap[parameter]
            parameterLiveData?.value = fetchedMessages

        }
    }

    private suspend fun fetchDataFromDataSource(param: String): String? {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(param)
        return if (valueOnLaunched != null) {
            val jsonOnLaunched = gson.toJson(valueOnLaunched)

            mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

            mapper.readValue(
                jsonOnLaunched,
                object : TypeReference<String>() {})
        } else {
            null
        }
    }

    fun setParam(param: String, value: String) {
        // Replace with your logic to delete the message from a data source
        senderAccess.sendRequest(param, value, null, null, null)
        // Remove the message from the list of messages
        val parameterLiveData = parameterLiveDataMap[param]
        parameterLiveData?.value = value
    }

    suspend fun validateSelection(): Boolean {
        val responseDeferred = CompletableDeferred<Boolean>()
        val propertyChangeListener = PropertyChangeListener { evt ->
            evt?.let {
                if (evt.propertyName == ApiEndpoints.SET_PARAM_EXT_DEV_COMM_TYPE) {
                    val newValue = evt.newValue.toString()
                    if (newValue == ApiResponses.OK) {
                        responseDeferred.complete(true)
                    } else if (newValue == ApiResponses.UC_BAPTIZED_UNABLE_TO_CHANGE_MODE) {
                        responseDeferred.complete(false)
                    }
                }
            }
        }
        addPropertyChangeListener(propertyChangeListener)
        return responseDeferred.await()
    }
}
