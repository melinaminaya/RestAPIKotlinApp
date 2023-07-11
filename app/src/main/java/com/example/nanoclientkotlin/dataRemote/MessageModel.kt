package com.example.nanoclientkotlin.dataRemote

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@JsonIgnoreProperties(ignoreUnknown = true)
data class DbMessage(

    @JsonProperty("msgCode") val code: Long?,
    @JsonProperty("isForward") val isForward: Boolean,
    @JsonProperty("msgStatusNum") val msgStatusNum: Int,
    @JsonProperty("msgSubtype") val subtype: Int,
    @JsonProperty("formCode") val formCode: Long,
    @JsonProperty("subject") val subject:String?,
    @JsonProperty("body") val body:String?,
    @JsonProperty("createdTime") var createdTime: Date?,
    @JsonProperty("sendReceivedTime") val sendReceivedTime: Date?,
    @JsonProperty("deliveryTime") val deliveryTime : Date?,
    @JsonProperty("gmn") val gmn:Long?,
    @JsonProperty("isOutOfBandMsg") val isOutOfBandMessage:Boolean,
    @JsonProperty("outOfBandFileName") val outOfBandFileName:String?,
    @JsonProperty("outOfBandNMsgStatusNum") val outOfBandNumMsgStatus:Int?,
    @JsonProperty("sourceAddress") val sourceAddress:String?,
    @JsonProperty("destAddress") val destAddress:String?,
    @JsonProperty("lastStatusTime") val lastStatusTime: Date?,
    @JsonProperty("msgPriority") val msgPriority:Int,
    @JsonProperty("replyGmn") val replyGmn:Long?,
    @JsonProperty("positionLatitude") val posLatitude:Int?,
    @JsonProperty("positionLongitude") val posLongitude:Int?,
    @JsonProperty("positionTime") val positionTime: Date?,
    @JsonProperty("positionSpeed") val posSpeed:Int?,
    @JsonProperty("positionHeading") val positionHeading:Float?,
    @JsonProperty("positionAging") val positionAging:Int?,
    @JsonProperty("transmissionChannel") val transmissionChannel: Int,
    @JsonProperty("transmittedChannel") val transmittedChannel:Int?,
    @JsonProperty("transmissionType") val transmissionType:Int,

)
