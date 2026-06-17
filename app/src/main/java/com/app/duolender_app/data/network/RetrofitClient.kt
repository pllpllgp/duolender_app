package com.app.duolender_app.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
	val baseUrl = "http://10.0.2.2:8081"
    val instance: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl+"/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
