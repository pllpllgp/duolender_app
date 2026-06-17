package com.app.duolender_app.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.duolender_app.data.auth.network.ScheduleApiService
import com.app.duolender_app.data.auth.network.SessionManager
import com.app.duolender_app.data.auth.request.ScheduleRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.String

class ScheduleViewModel(
	private val apiService: ScheduleApiService,
	private val sessionManager: SessionManager,
) : ViewModel() {
	//Schedule 등록 Model
	private val _registerStatus = MutableStateFlow<Boolean?>(null)
	val registerStatus: StateFlow<Boolean?> = _registerStatus.asStateFlow()

	fun register(title: String, startDate: String, endDate: String, memo: String) {
		viewModelScope.launch {
			try {
				val req = ScheduleRequest(
					scheduleId = 0,
					userId = sessionManager.getUserId(),
					scheduleNm = title,
					schduleStartDtm = startDate,
					schduleEndDtm = endDate,
					scheduleMemo = memo,
					scheduleGroupId = "",
					scheduleGroupNm = "",
					scheduleColor = "",
				)

				val res = apiService.scheduleRegister(req)

				if(res.isSuccessful) {
					_registerStatus.value = true // 성공 시뮬레이션

				} else {
					_registerStatus.value = false
				}

			} catch(e: Exception) {
				e.printStackTrace() // 로그캣에서 에러 원인을 보기 위해 출력
			}
		}
	}

}