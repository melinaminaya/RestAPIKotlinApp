package com.example.nanoclientkotlin.dataRemote

data class SendObject(val param1: String?, val param2: Any?)
data class ReceivedRequestResponse(
    val param1: String?,
    val param2: String?,
    val param3: Any?,
    val param4: Any?,
)

data class RequestObject(val param1: Any?, val param2: Any?, val param3: Any?, val param4: Any?)
data class ChunkObject(
    val message: String,
    val totalChunks: Int,
    val chunkNumber: Int,
    val data: String, // Use String to represent the chunk data
)
