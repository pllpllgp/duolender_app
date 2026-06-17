package com.app.duolender_app.data.auth.network

import com.app.duolender_app.data.auth.request.AuthRequest
import com.app.duolender_app.data.auth.request.LoginRequest
import com.app.duolender_app.data.auth.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
	@GET("auth/duoConnect")
	suspend fun connect(): Response<ResponseBody>

	@POST("auth/login")
	suspend fun login(
		@Body request: LoginRequest
	): Response<LoginResponse>

	//회원가입
	@POST("auth/signup")
	suspend fun signup(
		@Body request: AuthRequest
	): Response<ResponseBody>

}