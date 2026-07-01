package com.app.duolender_app.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
	val baseUrl = "http://10.0.2.2:8081"

	fun getInstance(sessionManager: SessionManager): Retrofit {
		val client = OkHttpClient.Builder()
			.addInterceptor { chain ->
				val token = sessionManager.getUserToken()
				val request = chain.request().newBuilder()
					.addHeader("Authorization", "Bearer $token")
					.build()
				chain.proceed(request)
			}
			.build()

		return Retrofit.Builder()
			.baseUrl(baseUrl + "/api/")
			.client(client)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}
}
