package br.com.autotrac.testnanoclient.vm

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.models.IntegrationMessage
import br.com.autotrac.testnanoclient.handlers.MessageSenderAccess
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class MessageViewModel (
): ViewModel() {

    private var _messages = MutableStateFlow<List<IntegrationMessage>>(emptyList())
    val messages: StateFlow<List<IntegrationMessage>> get() = _messages

    private var _outboxMessages = MutableLiveData<List<IntegrationMessage>>()
    val outboxMessages: MutableLiveData<List<IntegrationMessage>> get() = _outboxMessages

    private val senderAccess = MessageSenderAccess()
    private val gson = Gson()
    private val mapper = ObjectMapper()

    suspend fun fetchMessages() {
        senderAccess.sendRequest(
            ApiEndpoints.REQ_MESSAGE_LIST, 0,
            false, null, null)
        _messages.value = emptyList()
        val fetchedMessages: List<IntegrationMessage>? = fetchDataFromDataSource()
        _messages.value = fetchedMessages ?: emptyList()
        println("Current Messages: ${_messages.value.last()}")

    }
    suspend fun fetchOutboxMessages() {
        senderAccess.sendRequest(ApiEndpoints.REQ_MESSAGE_LIST, 0,
            true, null, null)
        _outboxMessages.value = emptyList<IntegrationMessage>()
        val fetchedMessages: List<IntegrationMessage>? = fetchDataFromDataSourceOutbox()
        val newMessages = fetchedMessages?.filter { message ->
            _outboxMessages.value?.none { it.code == message.code } ?: true
        }
        _outboxMessages.value = (_outboxMessages.value.orEmpty()) + (newMessages ?: emptyList())
    }

    // Function to delete a message
    suspend fun deleteMessage(message: IntegrationMessage) {
        deleteMessageFromDataSource(message)
        fetchMessages()
    }

    suspend fun deleteMessageOutbox(message: IntegrationMessage) {
        deleteMessageFromDataSource(message)
        fetchOutboxMessages()
    }
    // Function to mark a message as read
    suspend fun markMessageAsRead(message: IntegrationMessage) {
        markMessageAsReadInDataSource(message)
        fetchMessages()
    }
    private suspend fun fetchDataFromDataSource(): List<IntegrationMessage>? {
        delay(500)
          val  valueOnLaunched = ObservableUtil.getValue(ApiEndpoints.REQ_MESSAGE_LIST_INBOX)

        val jsonOnLaunched = gson.toJson(valueOnLaunched)
        val dateFormat = SimpleDateFormat("MMM d, yyyy HH:mm:ss", Locale.ENGLISH)
        mapper.dateFormat = dateFormat
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

        return mapper.readValue(jsonOnLaunched, object : TypeReference<MutableList<IntegrationMessage>>() {})
    }
    private suspend fun fetchDataFromDataSourceOutbox(): List<IntegrationMessage>? {
        delay(500)
        val valueOnLaunched = ObservableUtil.getValue(ApiEndpoints.REQ_MESSAGE_LIST_OUTBOX)

        val jsonOnLaunched = gson.toJson(valueOnLaunched)
        val dateFormat = SimpleDateFormat("MMM d, yyyy HH:mm:ss", Locale.ENGLISH)
        mapper.dateFormat = dateFormat
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)

        return mapper.readValue(jsonOnLaunched, object : TypeReference<MutableList<IntegrationMessage>>() {})
    }

    private fun deleteMessageFromDataSource(message: IntegrationMessage) {
        senderAccess.sendRequest(ApiEndpoints.REQ_MESSAGE_DELETE,
            message.code, null, null, null)
    }

    private fun markMessageAsReadInDataSource(message: IntegrationMessage) {
        senderAccess.sendRequest(ApiEndpoints.REQ_MESSAGE_SET_AS_READ,
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