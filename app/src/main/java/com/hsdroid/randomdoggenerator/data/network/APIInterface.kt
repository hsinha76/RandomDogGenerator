package com.hsdroid.randomdoggenerator.data.network

import com.google.gson.JsonObject
import com.hsdroid.randomdoggenerator.data.model.Dogs
import retrofit2.Response
import retrofit2.http.GET

interface APIInterface {

    @GET("/api/breeds/image/random")
    suspend fun getData() : JsonObject
}