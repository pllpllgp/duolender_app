package com.app.duolender_app.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.duolender_app.data.auth.network.ScheduleApiService
import com.app.duolender_app.data.auth.request.ScheduleRequest
import kotlinx.coroutines.launch
import kotlin.String

class ScheduleViewModel(private val apiService: ScheduleApiService) : ViewModel() {
	//Schedule 등록 Model
	fun register(title: String, startDate: String, endDate: String, memo: String) {
		viewModelScope.launch {
			try {
				val req = ScheduleRequest(
					scheduleId = 0,
					userId = "",
					scheduleNm = "",
					schduleStartDtm = "",
					schduleEndDtm = "",
					scheduleGroupId = "",
					scheduleGroupNm = "",
					scheduleColor = "",
				)
			} catch(e: Exception) {
				e.printStackTrace() // 로그캣에서 에러 원인을 보기 위해 출력
			}
		}
	}

}