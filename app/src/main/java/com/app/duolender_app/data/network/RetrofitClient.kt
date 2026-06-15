package com.app.duolender_app.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val instance: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8081/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
