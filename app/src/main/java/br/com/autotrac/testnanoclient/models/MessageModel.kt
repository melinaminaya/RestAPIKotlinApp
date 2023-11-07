package br.com.autotrac.testnanoclient.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

/**
 * Objeto de envio e de retorno das requisições que envolvem mensagens.
 *
 * @see br.com.autotrac.testnanoclient.consts.ApiEndpoints.REQ_MESSAGE_LIST
 * @see br.com.autotrac.testnanoclient.consts.ApiEndpoints.REQ_MESSAGE_DELETE
 * @see br.com.autotrac.testnanoclient.consts.ApiEndpoints.SEND_MESSAGE
 * @see br.com.autotrac.testnanoclient.consts.ApiEndpoints.SEND_FILE_MESSAGE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class IntegrationMessage(

    @JsonProperty("msgCode") val code: Long?,
    @JsonProperty("isForward") val isForward: Boolean,
    @JsonProperty("msgStatusNum") val msgStatusNum: Int,
    @JsonProperty("msgSubtype") val msgSubtype: Int,
    @JsonProperty("formCode") val formCode: Long,
    @JsonProperty("subject") val subject:String?,
    @JsonProperty("body") val body:String?,
    @JsonProperty("bodyBytes") val bodyBytes:ByteArray?,
    @JsonProperty("createdTime") var createdTime: Date?,
    @JsonProperty("sendReceivedTime") val sendReceivedTime: Date?,
    @JsonProperty("deliveryTime") val deliveryTime: Date?,
    @JsonProperty("gmn") val gmn:Long?,
    @JsonProperty("isOutOfBandMsg") val isOutOfBandMsg:Boolean,
    @JsonProperty("outOfBandFilename") val outOfBandFilename: String?,
    @JsonProperty("outOfBandMsgStatusNum") val outOfBandMsgStatusNum:Int?,
    @JsonProperty("sourceAddress") val sourceAddress:String?,
    @JsonProperty("destAddress") val destAddress:String?,
    @JsonProperty("lastStatusTime") val lastStatusTime: Date?,
    @JsonProperty("msgPriority") val msgPriority:Int,
    @JsonProperty("replyGmn") val replyGmn:Long?,
    @JsonProperty("positionLatitude") val positionLatitude:Int?,
    @JsonProperty("positionLongitude") val positionLongitude:Int?,
    @JsonProperty("positionTime") val positionTime: Date?,
    @JsonProperty("positionSpeed") val positionSpeed:Int?,
    @JsonProperty("positionHeading") val positionHeading:Float?,
    @JsonProperty("positionAging") val positionAging:Int?,
    @JsonProperty("transmissionChannel") val transmissionChannel: Int,
    @JsonProperty("transmittedChannel") val transmittedChannel:Int?,
    @JsonProperty("transmissionType") val transmissionType:Int,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IntegrationMessage

        if (bodyBytes != null) {
            if (other.bodyBytes == null) return false
            if (!bodyBytes.contentEquals(other.bodyBytes)) return false
        } else if (other.bodyBytes != null) return false

        return true
    }

    override fun hashCode(): Int {

        return bodyBytes?.contentHashCode() ?: 0
    }
}
