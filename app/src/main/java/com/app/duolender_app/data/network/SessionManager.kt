package com.app.duolender_app.data.network

import android.content.Context

class SessionManager(context: Context) {
	private val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

	fun saveSession(userId: String, userNm: String, userEmail: String, userPhone: String, userToken: String) {
		prefs.edit()
			.putString("userId", userId)
			.putString("userNm", userNm)
			.putString("userEmail", userEmail)
			.putString("userPhone", userPhone)
			.putString("userToken", userToken)
			.apply()

	}

	fun getUserId(): String = prefs.getString("userId", "") ?: ""
	fun getUserNm(): String = prefs.getString("userNm", "") ?: ""
	fun getUserToken(): String = prefs.getString("userToken", "") ?: ""

	fun isLoggedIn(): Boolean = getUserToken().isNotEmpty()

	fun clear() {
		prefs.edit().clear().apply()
	}

}