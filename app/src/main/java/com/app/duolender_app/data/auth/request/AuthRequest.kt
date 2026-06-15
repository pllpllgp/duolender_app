package com.app.duolender_app.data.auth.request

data class AuthRequest (
	val userId: String,
	val userPw: String,
	val userNm: String,
	val userEmail: String,
	val userPhone: String
)