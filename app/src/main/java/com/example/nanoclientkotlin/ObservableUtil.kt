package com.example.nanoclientkotlin

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport


object ObservableUtil {
    private val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)

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
        return when (propertyName) {
            "messageList" -> messageList

            else -> null
        }
    }

    fun <T> setValue(propertyName: String, value: T) {
        when (propertyName) {
            "messageList" ->
                    messageList = value



        }
    }

    var messageList: Any? = null
}
