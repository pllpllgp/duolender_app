package com.app.duolender_app.data.auth.request

data class AuthRequest (
	val userId: String,
	val userPw: String? = null,
	val userNm: String? = null,
	val userEmail: String? = null,
	val userPhone: String? = null,
)