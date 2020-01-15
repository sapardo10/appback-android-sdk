package com.appback.appbacksdk.network

import com.appback.appbacksdk.poko.AccessToken
import com.appback.appbacksdk.poko.log.BaseLogResponse
import com.appback.appbacksdk.poko.toggle.BaseToggleResponse
import com.appback.appbacksdk.poko.transalation.BaseTranslationResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AppbackApi {

    @GET("v1/translations")
    suspend fun loadTranslations(@Query("router") router: String): BaseTranslationResponse

    @GET("v1/toggles")
    suspend fun loadToggles(@Query("router") router: String): BaseToggleResponse

    @POST("v1/eventLog")
    suspend fun logEvent(
        @Query("router") router: String,
        @Query("name") name: String,
        @Query("description") description: String,
        @Query("level") level: Int
    ): BaseLogResponse

    @GET("token")
    suspend fun getToken(@Query("key") apikey: String): AccessToken

}