package com.example.nanoclientkotlin

import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.google.gson.Gson
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
            ConstsCommSvc.REQ_MESSAGE_LIST_INBOX -> messageListInbox
            ConstsCommSvc.REQ_MESSAGE_LIST_OUTBOX -> messageListOutbox
            "messageCount" -> transformJsonToInteger(messageCount.toString())
            ConstsCommSvc.REQ_FORM_LIST -> formList
            ConstsCommSvc.REQ_GET_CHECKLIST -> checkList
            ConstsCommSvc.REQ_GET_CURRENT_DATE -> currentDate
            ConstsCommSvc.REQ_GET_MCT_PARAMETERS -> mctParams
            ConstsCommSvc.REQ_GET_POSITION_LAST -> lastPosition
            ConstsCommSvc.REQ_POSITION_HISTORY_COUNT -> positionHistoryCount
            ConstsCommSvc.REQ_RESET_DATABASE -> resetDatabase

            else -> null
        }
    }

    fun <T> setValue(propertyName: String, value: T) {
        when (propertyName) {
            ConstsCommSvc.REQ_MESSAGE_LIST_INBOX -> messageListInbox = value
            ConstsCommSvc.REQ_MESSAGE_LIST_OUTBOX -> messageListOutbox = value
            "messageCount" -> messageCount = value
            ConstsCommSvc.REQ_FORM_LIST -> formList = value
            ConstsCommSvc.REQ_GET_CHECKLIST -> checkList = value
            ConstsCommSvc.REQ_GET_CURRENT_DATE -> currentDate = value
            ConstsCommSvc.REQ_GET_MCT_PARAMETERS -> mctParams = value
            ConstsCommSvc.REQ_GET_POSITION_LAST -> lastPosition = value
            ConstsCommSvc.REQ_POSITION_HISTORY_COUNT -> positionHistoryCount = value
            ConstsCommSvc.REQ_RESET_DATABASE -> resetDatabase = value

        }
    }

    var messageListInbox: Any? = null
    var messageListOutbox: Any? = null
    private var messageCount:Any? = null
    private var formList: Any? = null
    private var checkList: Any? = null
    private var currentDate: Any? = null
    private var mctParams: Any? = null
    private var lastPosition: Any? = null
    private var positionHistoryCount: Any? = null
    private var resetDatabase: Any? = null

    private fun transformJsonToInteger(json: String): Int? {
        val gson = Gson()
        return when (val parsedData = gson.fromJson(json, Long::class.java)) {
            is Long -> parsedData.toInt()
            else -> null // Return null if the parsed data is not a Long
        }
    }
}
