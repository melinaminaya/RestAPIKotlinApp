package br.com.autotrac.testnanoclient.vm

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.autotrac.testnanoclient.ObservableUtil
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.dataRemote.IntegrationMessage
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
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class MessageViewModel (
): ViewModel() {

    private val _messages = MutableLiveData<List<IntegrationMessage>>()
    val messages: MutableLiveData<List<IntegrationMessage>> get() = _messages

    private val _outboxMessages = MutableLiveData<List<IntegrationMessage>>()
    val outboxMessages: MutableLiveData<List<IntegrationMessage>> get() = _outboxMessages

    private val senderAccess = MessageSenderAccess()
    private val gson = Gson()
    private val mapper = ObjectMapper()

    private val _contentResolver = MutableLiveData<ContentResolver>()
    val contentResolverLiveData: LiveData<ContentResolver> = _contentResolver

    // Function to set the contentResolver in the ViewModel
    fun setContentResolver(contentResolver: ContentResolver) {
        _contentResolver.value = contentResolver
    }

    suspend fun fetchMessages() {
        senderAccess.sendRequest(
            ApiEndpoints.REQ_MESSAGE_LIST, 0,
            false, null, null)
//        sendMessageAndWait("messageList")
        // Replace with your logic to fetch messages from a data source
        val fetchedMessages: List<IntegrationMessage>? = fetchDataFromDataSource()
        // Filter out messages that already exist in the current list
        val newMessages = fetchedMessages?.filter { message ->
            _messages.value?.none { it.code == message.code } ?: true
        }
        // Append the new messages to the existing list
        _messages.value = _messages.value.orEmpty() + (newMessages ?: emptyList())

    }
    suspend fun fetchOutboxMessages() {
        senderAccess.sendRequest(ApiEndpoints.REQ_MESSAGE_LIST, 0,
            true, null, null)

        val fetchedMessages: List<IntegrationMessage>? = fetchDataFromDataSourceOutbox()
        val newMessages = fetchedMessages?.filter { message ->
            _outboxMessages.value?.none { it.code == message.code } ?: true
        }
        _outboxMessages.value = (_outboxMessages.value.orEmpty()) + (newMessages ?: emptyList())

    }

    // Function to delete a message
    fun deleteMessage(message: IntegrationMessage) {
        // Replace with your logic to delete the message from a data source
        deleteMessageFromDataSource(message)
        // Remove the message from the list of messages
        val currentMessages = _messages.value.orEmpty().toMutableList()
        currentMessages.remove(message)
        _messages.value = currentMessages
    }

    fun deleteMessageOutbox(message: IntegrationMessage) {
        // Replace with your logic to delete the message from a data source
        deleteMessageFromDataSource(message)
        // Remove the message from the list of messages
        val currentMessages = _outboxMessages.value.orEmpty().toMutableList()
        currentMessages.remove(message)
        _outboxMessages.value = currentMessages
    }
    // Function to mark a message as read
    fun markMessageAsRead(message: IntegrationMessage) {
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
    suspend fun fetchDataFromDataSource(): List<IntegrationMessage>? {
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