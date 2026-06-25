package com.app.duolender_app.data.schedule.network

import com.app.duolender_app.data.schedule.request.ScheduleRequest
import com.app.duolender_app.data.schedule.response.ScheduleResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ScheduleApiService {
	@POST("schedule/list")
	suspend fun scheduleList(
		@Body request: ScheduleRequest
	): Response<List<ScheduleResponse>>

	@POST("schedule/register")
	suspend fun scheduleRegister(
		@Body request: ScheduleRequest
	): Response<ResponseBody>

}