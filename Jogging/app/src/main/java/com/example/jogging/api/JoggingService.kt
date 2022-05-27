package com.example.jogging.api

import com.example.jogging.models.Jogging
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://10.0.2.2:4000"

interface JoggingService {
    @GET("jogging")
    fun getJoggings() : Call<List<Jogging>>
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object JoggingApi {
    val service: JoggingService by lazy { retrofit.create(JoggingService::class.java) }
}