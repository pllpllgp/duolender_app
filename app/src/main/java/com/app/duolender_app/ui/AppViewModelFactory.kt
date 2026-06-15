package com.app.duolender_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.duolender_app.data.auth.network.AuthApiService
import com.app.duolender_app.data.auth.network.ScheduleApiService
import com.app.duolender_app.data.network.RetrofitClient
import com.app.duolender_app.ui.auth.AuthViewModel
import com.app.duolender_app.ui.schedule.ScheduleViewModel

class AppViewModelFactory : ViewModelProvider.Factory {
    private val authApiService = RetrofitClient.instance.create(AuthApiService::class.java)
	private val scheduleApiService = RetrofitClient.instance.create(ScheduleApiService::class.java)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(authApiService) as T
			modelClass.isAssignableFrom(ScheduleViewModel::class.java) ->
				ScheduleViewModel(scheduleApiService) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: $modelClass")
        }
    }
}
