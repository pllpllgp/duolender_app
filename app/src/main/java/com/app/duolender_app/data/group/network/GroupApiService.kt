package com.app.duolender_app.data.group.network

import com.app.duolender_app.data.group.request.GroupRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GroupApiService {
	@POST("group/register")
	suspend fun groupRegister(
		@Body request: GroupRequest
	): Response<ResponseBody>
}