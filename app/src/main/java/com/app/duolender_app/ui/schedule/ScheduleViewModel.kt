package com.app.duolender_app.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.duolender_app.data.auth.network.ScheduleApiService
import com.app.duolender_app.data.auth.network.SessionManager
import com.app.duolender_app.data.auth.request.ScheduleRequest
import com.app.duolender_app.data.auth.response.ScheduleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.String
import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ScheduleViewModel(
	private val apiService: ScheduleApiService,
	private val sessionManager: SessionManager,
) : ViewModel() {
	//Schedule List
	private val _scheduleList = MutableStateFlow<List<ScheduleResponse>>(emptyList())
	val scheduleList: StateFlow<List<ScheduleResponse>> = _scheduleList.asStateFlow()

	val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))

	fun loadScheduleList(yearMonth: String) {
		viewModelScope.launch {
			try{
				val req = ScheduleRequest(
					userId = sessionManager.getUserId(),
					schScheduleDate = yearMonth
				)
				Log.d("ScheduleVM", "요청: userId=${req.userId}, yearMonth=$yearMonth")

				val res = apiService.scheduleList(req)

				if(res.isSuccessful) {
					val list = res.body() ?: emptyList()
					_scheduleList.value = list
					Log.d("ScheduleVM", "성공: ${list.size}건 → $list")
				} else {
					Log.e("ScheduleVM", "실패: HTTP ${res.code()} ${res.errorBody()?.string()}")
				}

			} catch (e: Exception) {
				Log.e("ScheduleVM", "에러: ${e.message}", e)
			}
		}
	}



	//Schedule 등록 Model
	private val _registerStatus = MutableStateFlow<Boolean?>(null)
	val registerStatus: StateFlow<Boolean?> = _registerStatus.asStateFlow()

	fun register(title: String, scheduleDtm: String, memo: String) {
		Log.d("ScheduleVM", "요청: startDate=${scheduleDtm}")
		viewModelScope.launch {
			try {
				val req = ScheduleRequest(
					userId = sessionManager.getUserId(),
					scheduleNm = title,
					scheduleDtm = scheduleDtm,
					scheduleMemo = memo,
					scheduleGroupId = "",
					scheduleGroupNm = "",
					scheduleColor = "",
					scheduleCrtnId = sessionManager.getUserId(),
					scheduleCrtnDtm = today,
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