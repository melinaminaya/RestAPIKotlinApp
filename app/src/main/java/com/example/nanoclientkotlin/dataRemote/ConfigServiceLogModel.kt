package com.example.nanoclientkotlin.dataRemote

import com.fasterxml.jackson.annotation.JsonProperty


data class ConfigServiceLogModel(
    @JsonProperty("enable") val enable: Boolean?,
    @JsonProperty("maxFileCount") val maxFileCountInt: Int?,
    @JsonProperty("maxFileSize") var maxFileSize: Long?,
)