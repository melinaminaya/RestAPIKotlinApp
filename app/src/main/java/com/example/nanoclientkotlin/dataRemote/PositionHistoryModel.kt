package com.example.nanoclientkotlin.dataRemote

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

data class PositionHistory(
    @JsonProperty("code") val code: Long?,
    @JsonProperty("latitude") val latitude:Long?,
    @JsonProperty("longitude") var longitude: Long?,
    @JsonProperty("positionTime") val positionTime : String?,
    @JsonProperty("speed") val speed:Long?,
    @JsonProperty("heading") val heading:Float?,
    @JsonProperty("aging") val aging:Long?,
    @JsonProperty("reason") val reason:Long?,
    @JsonProperty("lastStatusTime") val lastStatusTime:String?,
    @JsonProperty("transmittedChannel") val transmittedChannel:Int?,
    @JsonProperty("numMsgStatus") val numMsgStatus:Int?,
)