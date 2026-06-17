package com.app.duolender_app.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.duolender_app.data.auth.network.AuthApiService
import com.app.duolender_app.data.auth.network.ScheduleApiService
import com.app.duolender_app.data.auth.network.SessionManager
import com.app.duolender_app.data.network.RetrofitClient
import com.app.duolender_app.ui.auth.AuthViewModel
import com.app.duolender_app.ui.schedule.ScheduleViewModel

class AppViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val authApiService = RetrofitClient.instance.create(AuthApiService::class.java)
	private val scheduleApiService = RetrofitClient.instance.create(ScheduleApiService::class.java)
	private val sessionManager = SessionManager(context)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(authApiService, sessionManager) as T
			modelClass.isAssignableFrom(ScheduleViewModel::class.java) ->
				ScheduleViewModel(scheduleApiService, sessionManager) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: $modelClass")
        }
    }
}
