package com.example.nanoclientkotlin.vm

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nanoclientkotlin.MessageSenderAccess
import com.example.nanoclientkotlin.ObservableUtil
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.DbMessage
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class MessageViewModel (
): ViewModel() {

    private val _messages = MutableLiveData<List<DbMessage>>()
    val messages: MutableLiveData<List<DbMessage>> get() = _messages

    private val _outboxMessages = MutableLiveData<List<DbMessage>>()
    val outboxMessages: MutableLiveData<List<DbMessage>> get() = _outboxMessages

    private val senderAccess = MessageSenderAccess()
    private val gson = Gson()
    private val mapper = ObjectMapper()

    private val _contentResolver = MutableLiveData<ContentResolver>()
    val contentResolverLiveData: LiveData<ContentResolver> = _contentResolver

    // Function to set the contentResolver in the ViewModel
    fun setContentResolver(contentResolver: ContentResolver) {
        _contentResolver.value = contentResolver
    }


    // Function to fetch messages
    suspend fun fetchMessages() {
        senderAccess.sendRequest(ConstsCommSvc.REQ_MESSAGE_LIST, 0,
            false, null, null)
//        sendMessageAndWait("messageList")
        // Replace with your logic to fetch messages from a data source
        val fetchedMessages: List<DbMessage>? = fetchDataFromDataSource()
        // Filter out messages that already exist in the current list
        val newMessages = fetchedMessages?.filter { message ->
            _messages.value?.none { it.code == message.code } ?: true
        }
        // Append the new messages to the existing list
        _messages.value = _messages.value.orEmpty() + (newMessages ?: emptyList())

    }
    suspend fun fetchOutboxMessages() {
        senderAccess.sendRequest(ConstsCommSvc.REQ_MESSAGE_LIST, 0,
            true, null, null)

        val fetchedMessages: List<DbMessage>? = fetchDataFromDataSourceOutbox()
        val newMessages = fetchedMessages?.filter { message ->
            _outboxMessages.value?.none { it.code == message.code } ?: true
        }
        _outboxMessages.value = (_outboxMessages.value.orEmpty()) + (newMessages ?: emptyList())

    }

    // Function to delete a message
    fun deleteMessage(message: DbMessage) {
        // Replace with your logic to delete the message from a data source
        deleteMessageFromDataSource(message)
        // Remove the message from the list of messages
        val currentMessages = _messages.value.orEmpty().toMutableList()
        currentMessages.remove(message)
        _messages.value = currentMessages
    }

    // Function to mark a message as read
    fun markMessageAsRead(message: DbMessage) {
        // Replace with your logic to mark the message as read in a data source
        markMessageAsReadInDataSource(message)
        // Update the message status
        val currentMessages = _messages.value.orEmpty().toMutableList()
        val updatedMessage = message.copy(msgStatusNum =  4) // Assuming 4 represents "Lida"
        val index = currentMessages.indexOf(message)
        if (index != -1) {
            currentMessages[index] = updatedMessage
            _messages.value = currentMessages
        }
    }
    private suspend fun fetchDataFromDataSource(): List<DbMessage>? {
        delay(500)
          val  valueOnLaunched = ObservableUtil.getValue(ConstsCommSvc.REQ_MESSAGE_LIST_INBOX)

        val jsonOnLaunched = gson.toJson(valueOnLaunched)
        val dateFormat = SimpleDateFormat("MMM d, yyyy HH:mm:ss", Locale.ENGLISH)
        mapper.dateFormat = dateFormat
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

        return mapper.readValue(jsonOnLaunched, object : TypeReference<MutableList<DbMessage>>() {})
    }
    private suspend fun fetchDataFromDataSourceOutbox(): List<DbMessage>? {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(ConstsCommSvc.REQ_MESSAGE_LIST_OUTBOX)

        val jsonOnLaunched = gson.toJson(valueOnLaunched)
        val dateFormat = SimpleDateFormat("MMM d, yyyy HH:mm:ss", Locale.ENGLISH)
        mapper.dateFormat = dateFormat
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

        return mapper.readValue(jsonOnLaunched, object : TypeReference<MutableList<DbMessage>>() {})
    }

    private fun deleteMessageFromDataSource(message: DbMessage) {
        senderAccess.sendRequest(ConstsCommSvc.REQ_MESSAGE_DELETE,
            message.code, null, null, null)
    }

    private fun markMessageAsReadInDataSource(message: DbMessage) {
        senderAccess.sendRequest(ConstsCommSvc.REQ_MESSAGE_SET_AS_READ,
            message.code, null, null, null)
    }

    suspend fun sendMessageAndWait( message: String): Unit = withContext(Dispatchers.IO) {
        val senderAccess = MessageSenderAccess()
        return@withContext senderAccess.sendRequest(message, null,
            null, null, null)
    }


}
class CustomDateDeserializer : StdDeserializer<Date>(Date::class.java) {
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault())

    @Throws(ParseException::class)
    override fun deserialize(parser: JsonParser, context: DeserializationContext): Date? {
        val dateValue = parser.text.trim()
        return dateFormat.parse(dateValue)
    }
}