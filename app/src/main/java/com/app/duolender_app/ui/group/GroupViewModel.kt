package com.app.duolender_app.ui.group

import com.app.duolender_app.data.group.network.GroupApiService
import com.app.duolender_app.data.network.SessionManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.duolender_app.data.group.request.GroupRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GroupViewModel(
	private val apiService: GroupApiService,
	private val sessionManager: SessionManager,
) : ViewModel() {

	//Group 등록 Model
	private val _registerStatus = MutableStateFlow<Boolean?>(null)
	val registerStatus: StateFlow<Boolean?> = _registerStatus.asStateFlow()

	fun groupRegister(groupNm: String, groupMemo: String,) {
		viewModelScope.launch {
			try {
				val req = GroupRequest(
					groupNm = groupNm,
					groupCrtnId = sessionManager.getUserId(),
					groupMemo = groupMemo,
				)

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