package com.example.nanoclientkotlin.vm

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
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class MessageViewModel (
//    private val getGamesUseCase: GetGamesUseCase,
//    private val getGameByIdUseCase: GetGameByIdUseCase
): ViewModel() {

    private val _messages = MutableLiveData<List<DbMessage>>()
    val messages: MutableLiveData<List<DbMessage>> get() = _messages
    private val senderAccess = MessageSenderAccess()
    private val gson = Gson()
    private val mapper = ObjectMapper()


        init {
//            getGames()
        }
    // Function to fetch messages
    fun fetchMessages() {
        senderAccess.sendRequest("messageList", null, null, null)
//        sendMessageAndWait("messageList")
        // Replace with your logic to fetch messages from a data source
        val fetchedMessages: List<DbMessage> = fetchDataFromDataSource()
        _messages.value = fetchedMessages
        val messages =  listOf(
            DbMessage(
                code = 1L,
                isForward = false,
                msgStatusNum = 0,
                subtype = 1,
                formCode = 123L,
                subject = "Sample Subject 1",
                body = null,
                createdTime = "2023-05-30 12:00:00",
                sendReceivedTime = "2023-05-30 12:10:00",
                deliveryTime = "2023-05-30 12:15:00",
                gmn = 12345L,
                isOutOfBandMessage = false,
                outOfBandFileName = null,
                outOfBandNumMsgStatus = 0,
                sourceAddress = "sender@example.com",
                destAddress = "recipient@example.com",
                lastStatusTime = "2023-05-30 12:20:00",
                msgPriority = 2,
                replyGmn = 0L,
                posLatitude = 0,
                posLongitude = 0,
                positionTime = null,
                posSpeed = 0,
                positionHeading = 0.0f,
                positionAging = 0,
                transmissionChannel = 1,
                transmittedChannel = 1,
                transmissionType = 1
            ),
            DbMessage(
                code = 2L,
                isForward = true,
                msgStatusNum = 1,
                subtype = 2,
                formCode = 456L,
                subject = "Sample Subject 2",
                body = null,
                createdTime = "2023-05-30 13:00:00",
                sendReceivedTime = "2023-05-30 13:10:00",
                deliveryTime = "2023-05-30 13:15:00",
                gmn = 67890L,
                isOutOfBandMessage = true,
                outOfBandFileName = "attachment.txt",
                outOfBandNumMsgStatus = 1,
                sourceAddress = "sender@example.com",
                destAddress = "recipient@example.com",
                lastStatusTime = "2023-05-30 13:20:00",
                msgPriority = 1,
                replyGmn = 0L,
                posLatitude = 0,
                posLongitude = 0,
                positionTime = null,
                posSpeed = 0,
                positionHeading = 0.0f,
                positionAging = 0,
                transmissionChannel = 2,
                transmittedChannel = 2,
                transmissionType = 2
            ),
            DbMessage(
                code = 3L,
                isForward = false,
                msgStatusNum = 2,
                subtype = 3,
                formCode = 789L,
                subject = "Sample Subject 3",
                body = null,
                createdTime = "2023-05-30 14:00:00",
                sendReceivedTime = "2023-05-30 14:10:00",
                deliveryTime = "2023-05-30 14:15:00",
                gmn = 13579L,
                isOutOfBandMessage = false,
                outOfBandFileName = null,
                outOfBandNumMsgStatus = 0,
                sourceAddress = "sender@example.com",
                destAddress = "recipient@example.com",
                lastStatusTime = "2023-05-30 14:20:00",
                msgPriority = 0,
                replyGmn = 0L,
                posLatitude = 0,
                posLongitude = 0,
                positionTime = null,
                posSpeed = 0,
                positionHeading = 0.0f,
                positionAging = 0,
                transmissionChannel = 3,
                transmittedChannel = 3,
                transmissionType = 3
            )
        )
//        _messages.value = messages
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

    // Function to confirm message read
    fun confirmMessageRead() {
        // Replace with your logic to confirm the message read
        // For example, you can show a toast or perform any other action
    }

    // Dummy functions for demonstration purposes
    private fun fetchDataFromDataSource(): List<DbMessage> {
        val valueOnLaunched = ObservableUtil.getValue("messageList")
        val jsonOnLaunched = gson.toJson(valueOnLaunched)
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//            .setDateFormat(StdDateFormat())
//            .registerKotlinModule()
//
//        val simpleModule = SimpleModule()
//            .addDeserializer(Date::class.java, CustomDateDeserializer())
//
//        mapper.registerModule(simpleModule)

        return mapper.readValue(jsonOnLaunched, object : TypeReference<MutableList<DbMessage>>() {})
    }

    private fun deleteMessageFromDataSource(message: DbMessage) {
        senderAccess.sendRequest("messageDelete", message.code, null, null)
    }

    private fun markMessageAsReadInDataSource(message: DbMessage) {
        senderAccess.sendRequest(ConstsCommSvc.REQ_MESSAGE_SET_AS_READ, message.code, null, null)
    }

    suspend fun sendMessageAndWait( message: String): Unit = withContext(Dispatchers.IO) {
        val senderAccess = MessageSenderAccess()
        return@withContext senderAccess.sendRequest(message, null, null, null)
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