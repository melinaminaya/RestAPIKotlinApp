package com.example.nanoclientkotlin

import androidx.lifecycle.MutableLiveData
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.google.gson.Gson
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport


object ObservableUtil {
    private val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val parameterMap = HashMap<String, Any?>()

    init {
        ConstsCommSvc.parametersList.forEach { parameter ->
            parameterMap[parameter] = null
        }
        ConstsCommSvc.requestsListForObservables.forEach { parameter ->
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
