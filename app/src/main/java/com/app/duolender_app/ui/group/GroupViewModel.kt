package com.app.duolender_app.ui.group

import android.util.Log
import com.app.duolender_app.data.group.network.GroupApiService
import com.app.duolender_app.data.network.SessionManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.duolender_app.data.group.request.GroupRequest
import com.app.duolender_app.data.group.response.GroupResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GroupViewModel(
	private val apiService: GroupApiService,
	private val sessionManager: SessionManager,
) : ViewModel() {
	//Schedule List
	private val _groupList = MutableStateFlow<List<GroupResponse>>(emptyList())
	val groupList: StateFlow<List<GroupResponse>> = _groupList.asStateFlow()

	fun loadGroupList(groupNm: String) {
		viewModelScope.launch {
			try{
				val req = GroupRequest(
					groupNm = groupNm,
				)

				val res = apiService.groupList(req)

				if(res.isSuccessful) {
					val list = res.body() ?: emptyList()
					_groupList.value = list
					Log.d("ScheduleVM", "성공: ${list.size}건 → $list")
				} else {
					Log.e("ScheduleVM", "실패: HTTP ${res.code()} ${res.errorBody()?.string()}")
				}

			} catch (e: Exception) {
				Log.e("ScheduleVM", "에러: ${e.message}", e)
			}
		}
	}


	//group register Model
	private val _registerStatus = MutableStateFlow<Boolean?>(null)
	val registerStatus: StateFlow<Boolean?> = _registerStatus.asStateFlow()

	fun groupRegister(groupNm: String, groupMemo: String,) {
		viewModelScope.launch {
			try {
				val req = GroupRequest(
					userId = sessionManager.getUserId(),
					groupNm = groupNm,
					groupMemo = groupMemo,
				)
				Log.d("ScheduleVM", "요청: userId=${req.groupCrtnId}")
				val res = apiService.groupRegister(req)

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