package com.app.duolender_app.data.auth.network

import com.app.duolender_app.data.auth.request.LoginRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ScheduleApiService {
	@POST("api/auth/login")
	suspend fun scheduleRegister(
		@Body request: LoginRequest
	): Response<ResponseBody>

}