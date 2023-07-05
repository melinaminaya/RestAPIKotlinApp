package com.example.nanoclientkotlin.dataRemote

import com.example.nanoclientkotlin.consts.ConstsCommSvc.Companion.AUTH_ENDPOINT
import okhttp3.Response
import retrofit2.http.GET

interface MessageApi {

    @GET(AUTH_ENDPOINT)
    suspend fun getAuth(): Response

//    @GET(GAME_ID_ENDPOINT)
//    suspend fun getGameById(@Query(value = "id") id: Int): Response<SpecificGameModel>

}