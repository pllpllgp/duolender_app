package com.app.duolender_app.ui.schedule

import androidx.lifecycle.ViewModel
import com.app.duolender_app.data.auth.network.ScheduleApiService

class ScheduleViewModel(private val apiService: ScheduleApiService) : ViewModel() {
	//Schedule 등록 Model
	fun register(title: String, isAllDay: Boolean, startDate: String, startTime: String, endDate: String, endTime: String, memo: String) {

	}

}