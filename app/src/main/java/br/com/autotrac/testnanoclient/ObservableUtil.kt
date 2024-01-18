package br.com.autotrac.testnanoclient

import br.com.autotrac.testnanoclient.handlers.EndpointsLists
import com.google.gson.Gson
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

/**
 * ObservableUtil support class for property change,
 * when received by the server.
 * @author Melina Minaya
 */
object ObservableUtil {
    private val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val parameterMap = HashMap<String, Any?>()

    init {
        EndpointsLists.parametersList.forEach { parameter ->
            parameterMap[parameter] = null
        }
        EndpointsLists.requestsListForObservables.forEach { parameter ->
            parameterMap[parameter] = null
        }
    }
    fun addPropertyChangeListener(listener: PropertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(listener)
    }

    fun removePropertyChangeListener(listener: PropertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(listener)
    }

    fun <T> attachProperty(propertyName: String, value: T) {
        val oldValue = getValue(propertyName)
        setValue(propertyName, value)
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, value)
    }
    fun getValue(propertyName: String): Any? {
        return parameterMap[propertyName]

    }

    fun <T> setValue(propertyName: String, value: T) {
        parameterMap[propertyName] = value

    }


    fun transformJsonToInteger(json: String): Int? {
        val gson = Gson()
        return when (val parsedData = gson.fromJson(json, Long::class.java)) {
            is Long -> parsedData.toInt()
            else -> null // Return null if the parsed data is not a Long
        }
    }
}

/**
 * Observable constants for internal use.
 */
class ApiObservableConsts() {
    companion object {
        const val MESSAGE_COUNT = "MESSAGE_COUNT"
        const val MESSAGE_CODE = "MESSAGE_CODE"
        const val MESSAGE_STATUS = "MESSAGE_STATUS"
        const val BAPTISM_STATUS = "BAPTISM_STATUS"
        const val COMMUNICATION_MODE_CHANGED = "COMMUNICATION_MODE_CHANGED"
        const val DATE_TIME_CHANGED = "DATE_TIME_CHANGED"
        const val FILE_OPERATION_STATUS = "FILE_OPERATION_STATUS"
        const val FORM_DELETED = "FORM_DELETED"
        const val FORM_RECEIVED = "FORM_RECEIVED"
        const val IGNITION_STATUS = "IGNITION_STATUS"
        const val MCT_M0_PARAM = "MCT_M0_PARAM"
        const val MCT_M2_PARAM = "MCT_M2_PARAM"
        const val MCT_M0_M9_PARAMS_UPDATED = "MCT_M0_M9_PARAMS_UPDATED"
        const val NETWORK_CONNECTION_STATUS = "NETWORK_CONNECTION_STATUS"
        const val NETWORK_CONNECTION_TYPE = "NETWORK_CONNECTION_TYPE"
        const val READY_TO_UPDATE_SOFTWARE = "READY_TO_UPDATE_SOFTWARE"
        const val SATELLITE_SIGNAL_CHANGED = "SATELLITE_SIGNAL_CHANGED"
        const val SIGN_ON_STATUS = "SIGN_ON_STATUS"
        const val SYSTEM_RESOURCE_REQ_STATUS = "SYSTEM_RESOURCE_REQ_STATUS"
        const val SYSTEM_RESOURCE_REQ_TYPE = "SYSTEM_RESOURCE_REQ_TYPE"
        const val UPDATE_SERVER_PARAMETER = "UPDATE_SERVER_PARAMETER"
        val allConstants = listOf(
            MESSAGE_COUNT, MESSAGE_CODE, MESSAGE_STATUS, BAPTISM_STATUS,
            COMMUNICATION_MODE_CHANGED, DATE_TIME_CHANGED, FILE_OPERATION_STATUS,
            FORM_DELETED, FORM_RECEIVED, IGNITION_STATUS, MCT_M0_PARAM,
            MCT_M2_PARAM, MCT_M0_M9_PARAMS_UPDATED, NETWORK_CONNECTION_STATUS,
            NETWORK_CONNECTION_TYPE, READY_TO_UPDATE_SOFTWARE, SATELLITE_SIGNAL_CHANGED,
            SIGN_ON_STATUS, SYSTEM_RESOURCE_REQ_STATUS, SYSTEM_RESOURCE_REQ_TYPE,
            UPDATE_SERVER_PARAMETER
        )
    }
}