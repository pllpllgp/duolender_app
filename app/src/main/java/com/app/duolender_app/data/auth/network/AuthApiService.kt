package com.app.duolender_app.data.auth.network

import com.app.duolender_app.data.auth.request.AuthRequest
import com.app.duolender_app.data.auth.request.LoginRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
	@GET("api/auth/duoConnect")
	suspend fun connect(): Response<ResponseBody>

	@POST("api/auth/login")
	suspend fun login(
		@Body request: LoginRequest
	): Response<ResponseBody>

	//회원가입
	@POST("api/auth/signup")
	suspend fun signup(
		@Body request: AuthRequest
	): Response<ResponseBody>

}