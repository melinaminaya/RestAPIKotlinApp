package br.com.autotrac.testnanoclient.di

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    /**
     * Requisição Genérica de endpoints
     */
    @GET("/{endpoint}")
    fun callRequest(
        @Header("authorization") authorization: String,
        @Path("endpoint") endpoint: String,
        @Query("param1") param1: Any?,
        @Query("param2") param2: Any?,
        @Query("param3") param3: Any?,
        @Query("param4") param4: Any?,
    ): Call<ResponseBody>
}
