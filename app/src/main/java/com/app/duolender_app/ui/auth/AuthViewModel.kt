package com.app.duolender_app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.duolender_app.data.auth.network.AuthApiService
import com.app.duolender_app.data.auth.request.AuthRequest
import com.app.duolender_app.data.auth.request.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val apiService: AuthApiService) : ViewModel() {
	enum class ConnectionStatus {
		CHECKING,
		CONNECTED,
		DISCONNECTED,
	}

	private val _connectionStatus = MutableStateFlow(ConnectionStatus.CHECKING)
	val connectionStatus: StateFlow<ConnectionStatus> = _connectionStatus.asStateFlow()

	init {
		checkConnection()
	}

	private fun checkConnection() {
		viewModelScope.launch {
			try {
				val res = apiService.connect()
				_connectionStatus.value = if (res.isSuccessful) {
					ConnectionStatus.CONNECTED
				} else {
					ConnectionStatus.DISCONNECTED
				}

			} catch (e: Exception) {
				_connectionStatus.value = ConnectionStatus.DISCONNECTED
			}
		}
	}

	// 로그인 Model
	private val _userId = MutableStateFlow("")
	val userId: StateFlow<String> = _userId.asStateFlow()
	fun onEmailChange(value: String) {
		_userId.value = value
	}

	private val _userPw = MutableStateFlow("")
	val userPw: StateFlow<String> = _userPw.asStateFlow()
	fun onPasswordChange(value: String) {
		_userPw.value = value
	}

	private val _loginStatus = MutableStateFlow<Boolean?>(null)
	val loginStatus: StateFlow<Boolean?> = _loginStatus.asStateFlow()

	fun login() {
		// (임시 로그인 검증 로직 - 나중에 로그인 API를 연결할 곳입니다)
		viewModelScope.launch {
			if (userId.value.isNotBlank() && userPw.value.isNotBlank()) {
				try {
					val req = LoginRequest(
						userId = userId.value,
						userPw = userPw.value,
					)

					val res = apiService.login(req)

					if(res.isSuccessful) {
						_loginStatus.value = true // 성공 시뮬레이션
					} else {
						_loginStatus.value = false
					}

				} catch(e: Exception) {
					e.printStackTrace() // 로그캣에서 에러 원인을 보기 위해 출력
					_loginStatus.value = false
				}

			} else {
				_loginStatus.value = false
			}
		}
	}

	// 회원가입 Model
	private val _signupResult = MutableStateFlow<Result<String>?>(null)
	val signupResult: StateFlow<Result<String>?> = _signupResult.asStateFlow()

	fun signup(userId: String, userPw: String, userNm: String, userEmail: String, userPhone: String) {
		viewModelScope.launch {
			try {
				val req = AuthRequest(
					userId = userId,
					userPw = userPw,
					userNm = userNm,
					userEmail = userEmail,
					userPhone = userPhone
				)

				val res = apiService.signup(req)

				if(res.isSuccessful) {
					_signupResult.value = Result.success("회원가입 성공")
				} else {
					_signupResult.value = Result.failure(Exception("회원가입 실패: ${res.code()}"))
				}

			} catch(e: Exception) {
				e.printStackTrace() // 로그캣에서 에러 원인을 보기 위해 출력
				_signupResult.value = Result.failure(Exception("네트워크 에러가 발생했습니다: ${e.message}"))
			}
		}
	}

	// 상태 초기화용
	fun resetResult() {
		_signupResult.value = null
	}
}