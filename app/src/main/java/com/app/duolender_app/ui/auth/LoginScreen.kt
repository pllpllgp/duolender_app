package com.app.duolender_app.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.duolender_app.ui.AppViewModelFactory

@Composable
fun LoginScreen(
	viewModel: AuthViewModel = viewModel(factory = AppViewModelFactory()),
	onLoginSuccess: () -> Unit,
	onNavigateToSignup: () -> Unit,
) {
	val userId by viewModel.userId.collectAsState()
	val userPw by viewModel.userPw.collectAsState()
	val loginStatus by viewModel.loginStatus.collectAsState()

	LaunchedEffect(loginStatus) {
		if (loginStatus == true) {
			onLoginSuccess()
		}
	}

	val connectionStatus by viewModel.connectionStatus.collectAsState()

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(24.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center // 화면 정중앙에 배치
	) {
		val (statusText, statusColor) = when (connectionStatus) {
			AuthViewModel.ConnectionStatus.CHECKING     -> "서버 연결 중..." to MaterialTheme.colorScheme.onSurfaceVariant
			AuthViewModel.ConnectionStatus.CONNECTED    -> "서버 연결됨"     to MaterialTheme.colorScheme.primary
			AuthViewModel.ConnectionStatus.DISCONNECTED -> "서버 연결 실패"  to MaterialTheme.colorScheme.error
		}
		Text(text = statusText, fontSize = 12.sp, color = statusColor)

		Spacer(modifier = Modifier.height(8.dp))

		// [앱 로고/타이틀 영역]
		Text(
			text = "DuoLender",
			fontSize = 36.sp,
			fontWeight = FontWeight.ExtraBold,
			color = MaterialTheme.colorScheme.primary
		)
		Spacer(modifier = Modifier.height(8.dp))
		Text(
			text = "우리들의 그룹 일정 관리",
			fontSize = 16.sp,
			color = MaterialTheme.colorScheme.onSurfaceVariant
		)

		Spacer(modifier = Modifier.height(48.dp))

		// [이메일 입력 필드]
		OutlinedTextField(
			value = userId,
			onValueChange = { viewModel.onEmailChange(it) },
			label = { Text("아이디") },
			modifier = Modifier.fillMaxWidth(),
			singleLine = true
		)

		Spacer(modifier = Modifier.height(16.dp))

		// [비밀번호 입력 필드]
		OutlinedTextField(
			value = userPw,
			onValueChange = { viewModel.onPasswordChange(it) },
			label = { Text("비밀번호") },
			modifier = Modifier.fillMaxWidth(),
			singleLine = true,
			visualTransformation = PasswordVisualTransformation() // 비밀번호를 *** 로 가려줌
		)

		Spacer(modifier = Modifier.height(32.dp))

		// [로그인 버튼]
		Button(
			onClick = { viewModel.login() },
			modifier = Modifier
				.fillMaxWidth()
				.height(50.dp)
		) {
			Text("로그인", fontSize = 16.sp, fontWeight = FontWeight.Bold)
		}

		// 로그인 실패 시 나타나는 에러 메시지
		if (loginStatus == false) {
			Spacer(modifier = Modifier.height(16.dp))
			Text("이메일과 비밀번호를 입력해주세요.", color = MaterialTheme.colorScheme.error)
		}

		TextButton(onClick = onNavigateToSignup) {
			Text(
				text = "아직 회원이 아니신가요? 회원가입",
				fontSize = 14.sp,
				color = MaterialTheme.colorScheme.primary
			)
		}
	}

}