package com.app.duolender_app.data.auth.network

import com.app.duolender_app.data.auth.request.ScheduleRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ScheduleApiService {
	@POST("api/schedule/register")
	suspend fun scheduleRegister(
		@Body request: ScheduleRequest
	): Response<ResponseBody>

}