package com.app.duolender_app.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
	signupResult: Result<String>?,
	onSignupClick: (userId: String, userPw: String, userNm: String, userEmail: String, userPhone: String) -> Unit,
	onBackToLogin: () -> Unit,
) {
	var userId by remember { mutableStateOf("") }
	var userPw by remember { mutableStateOf("") }
	var userNm by remember { mutableStateOf("") }
	var userEmail by remember { mutableStateOf("") }
	var userPhone by remember { mutableStateOf("") }
	var errorMessage by remember { mutableStateOf("") }

	val scrollState = rememberScrollState()

	LaunchedEffect(signupResult) {
		when {
			signupResult?.isSuccess == true -> onBackToLogin()
			signupResult?.isFailure == true -> errorMessage = signupResult.exceptionOrNull()?.message ?: "오류가 발생했습니다."
		}
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(24.dp)
			.verticalScroll(scrollState), // 스크롤 활성화
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Text(
			text = "회원가입",
			fontSize = 28.sp,
			style = MaterialTheme.typography.headlineMedium,
			modifier = Modifier.padding(bottom = 32.dp, top = 32.dp)
		)

		// 1. 아이디 입력창 (user_id)
		OutlinedTextField(
			value = userId,
			onValueChange = { userId = it },
			label = { Text("아이디") },
			singleLine = true,
			modifier = Modifier.fillMaxWidth()
		)

		Spacer(modifier = Modifier.height(16.dp))

		// 2. 비밀번호 입력창 (user_pw)
		OutlinedTextField(
			value = userPw,
			onValueChange = { userPw = it },
			label = { Text("비밀번호") },
			singleLine = true,
			visualTransformation = PasswordVisualTransformation(),
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
			modifier = Modifier.fillMaxWidth()
		)

		Spacer(modifier = Modifier.height(16.dp))

		// 3. 이름 입력창 (user_nm)
		OutlinedTextField(
			value = userNm,
			onValueChange = { userNm = it },
			label = { Text("이름") },
			singleLine = true,
			modifier = Modifier.fillMaxWidth()
		)

		Spacer(modifier = Modifier.height(16.dp))

		// 4. 이메일 입력창 (user_email)
		OutlinedTextField(
			value = userEmail,
			onValueChange = { userEmail = it },
			label = { Text("이메일") },
			singleLine = true,
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
			modifier = Modifier.fillMaxWidth()
		)

		Spacer(modifier = Modifier.height(16.dp))

		// 5. 전화번호 입력창 (user_phone)
		OutlinedTextField(
			value = userPhone,
			onValueChange = { userPhone = it },
			label = { Text("전화번호 ('-' 제외)") },
			singleLine = true,
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
			modifier = Modifier.fillMaxWidth()
		)

		Spacer(modifier = Modifier.height(32.dp))

		// 회원가입 버튼
		Button(
			onClick = {
				// 유효성 검사 (빈칸 확인)
				if (userId.isBlank() || userPw.isBlank() || userNm.isBlank() || userEmail.isBlank() || userPhone.isBlank()) {
					errorMessage = "모든 필드를 입력해 주세요."
				} else {
					errorMessage = ""
					// ViewModel이나 Navigation으로 데이터를 넘겨주는 콜백 실행
					onSignupClick(userId, userPw, userNm, userEmail, userPhone)
				}
			},
			modifier = Modifier.fillMaxWidth().height(50.dp)
		) {
			Text("가입하기")
		}

		// 에러 메시지 출력
		if (errorMessage.isNotEmpty()) {
			Spacer(modifier = Modifier.height(16.dp))
			Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
		}

		Spacer(modifier = Modifier.height(16.dp))

		// 로그인 화면으로 돌아가기 버튼
		TextButton(onClick = onBackToLogin) {
			Text("이미 계정이 있으신가요? 로그인하기")
		}
	}

}