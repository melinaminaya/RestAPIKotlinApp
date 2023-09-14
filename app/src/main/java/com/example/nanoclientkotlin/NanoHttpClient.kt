package com.example.nanoclientkotlin

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.dataRemote.DbMessage
import com.example.nanoclientkotlin.dataRemote.RequestObject
import com.example.nanoclientkotlin.di.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Cliente de integração para o servidor NanoHTTPD.
 * Utiliza OkHttpClient e Retrofit.
 */
object NanoHttpClient {
    private val gson = Gson()
    private const val serverUrl =
        "http://127.0.0.1:8081" // Replace with your NanoHTTPD WebSocket server URL
    private const val MAX_RETRIES = 5
    private const val RETRY_DELAY_MS: Long = 5000 // 1 second
    private const val packageName = "com.example.nanoclientkotlin"
    private val client = OkHttpClient.Builder()
    const val TAG = "NanoHttpClient"


    private fun requestAuthorizationToken(): String? {
        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS) // Increase the timeout duration as needed
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url("${serverUrl}/auth") // Replace with your server's endpoint URL
            .addHeader("User-Agent", System.getProperty("http.agent")!!)
            .addHeader("Package-Name", NanoWebsocketClient.packageName)
            .build()
        var response: okhttp3.Response? = null
        var retryCount = 0
        while (response == null || !response.isSuccessful) {
            try {
                response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    // Parse the response body to extract the authorization token
                    // val treatTokenReceived = tokenReceived!!.replace("\n", "")
                    return response.body?.string()
                    Log.d("NanoHttpClient", "Token received")
                    //parseAuthorizationToken(responseBody)
                } else {
                    // Handle error response
                    // Retry connection if retry count is within the limit
                    if (retryCount < MAX_RETRIES) {
                        retryCount++
                        Thread.sleep(RETRY_DELAY_MS)
                    } else {
                        // Retry limit exceeded, return null or throw an exception
                        return null
                    }
                    Log.e("NanoHttpClient", "Error response: $response")
                }
            } catch (e: IOException) {
                if (retryCount < MAX_RETRIES) {
                    retryCount++
                    e.printStackTrace()
                    Thread.sleep(RETRY_DELAY_MS)
                } else {
                    // Retry limit exceeded, return null or throw an exception
                    return null
                }

            } catch (e: InterruptedException) {
                // Interrupted while waiting, return null or throw an exception
                return null
            } finally {
                // Close the response body if it is not null
                response?.body?.close()
            }
        }
        return null
    }

    suspend fun sendGetRequestHttp( endpoint: String, objectGet: RequestObject): String {
        // ... existing code for sendGetRequest ...
        var token: String? = null
        var responseBody: ResponseBody? = null
        return withContext(Dispatchers.IO) {
            while (token.isNullOrBlank()) {
                token = requestAuthorizationToken()
                if (token.isNullOrBlank()) {
                    delay(100) // Wait for 1 second before retrying
                    Log.d("NanoHttpClient", "Token is null ")
                }
            }

            // Construct the URL for the additional request
            val additionalRequestUrl = "$serverUrl/$endpoint/?token=$token"
            val customHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS) // Set your custom timeout here
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()

            // Create a new request using the additional URL
            val additionalRequest = Retrofit.Builder()
                .baseUrl(additionalRequestUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(customHttpClient)
                .build()

            val service = additionalRequest.create(ApiService::class.java)
            val headers = "$token"

            val listOfRequests = ConstsCommSvc.requestsList + ConstsCommSvc.parametersList +
                    listOf<String>(ConstsCommSvc.SEND_MESSAGE, ConstsCommSvc.SEND_FILE_MESSAGE)

            val response:Call<ResponseBody> = when (endpoint) {

                in listOfRequests ->{
                    service.callRequest(
                        headers,
                        endpoint,
                        objectGet.param1,
                        objectGet.param2,
                        objectGet.param3,
                        objectGet.param4,

                    )
                }

                else -> {
                    Log.e(TAG, "Endpoint not supported: $endpoint")
                    return@withContext ""
                }
            }



            // Execute the additional request

            responseBody = response!!.execute().body()
            if (responseBody != null) {
                val responseData = responseBody!!.string()
                println("Response from $endpoint: $responseData")
                return@withContext responseData
            } else {
                println("Response body is null for $endpoint")
                return@withContext ""
            }
        }
    }

    suspend fun sendFileChunksHttp(
        endpoint: String,
        message: DbMessage,
        maxChunkSize: Int,
        fileUri: Uri,
        context: Context
    ): String {
        var  response: String = ""
        val messageSenderAccess = MessageSenderAccess()
        val coroutineScope = CoroutineScope(context = SupervisorJob() + Dispatchers.IO)


        Thread {
           try {
                NanoWebsocketClient.connect()
                Thread.sleep(1000)
                if (NanoWebsocketClient.isWebSocketConnected()) {
                    coroutineScope.launch(Dispatchers.IO) {

                       response = messageSenderAccess.sendMessageToServerHttp(
                            message,
                            context = context,
                            fileUri = fileUri
                        )

                    }

                }
            }catch (e: Exception) {e.printStackTrace()
           }

        }.start()

        while (response == ""){
            Thread.sleep(1000)
        }


       NanoWebsocketClient.disconnect()
       return  response




    }
    private fun getFilePathFromContentUri(context: Context, contentUri: Uri): String? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(contentUri, filePathColumn, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        val filePath = cursor?.getString(columnIndex ?: -1)
        cursor?.close()
        return filePath
    }
    fun uriToBytes(context: Context, uri: Uri): ByteArray? {
        var inputStream: InputStream? = null
        try {
            inputStream = context.contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val byteBuffer = ByteArrayOutputStream()
                val bufferSize = 1024
                val buffer = ByteArray(bufferSize)
                var bytesRead: Int
                while (inputStream.read(buffer, 0, bufferSize).also { bytesRead = it } != -1) {
                    byteBuffer.write(buffer, 0, bytesRead)
                }
                return byteBuffer.toByteArray()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }



}

