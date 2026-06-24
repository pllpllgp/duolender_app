package com.app.duolender_app.ui.schedule

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.duolender_app.ui.AppViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
	scheduleDtm: String,
	onBackClick: () -> Unit,
	onSaveClick: (title: String, scheduleDtm: String, memo: String) -> Unit,
	onRegisterSuccess: () -> Unit,
) {
	val context = LocalContext.current
	val viewModel: ScheduleViewModel = viewModel(factory = AppViewModelFactory(context))

	val registerStatus by viewModel.registerStatus.collectAsState()

	LaunchedEffect(registerStatus) {
		if (registerStatus == true) {
			onRegisterSuccess()
		}
	}

	var title by remember { mutableStateOf("") }
	var memo by remember { mutableStateOf("") }

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("일정 추가", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
				navigationIcon = {
					IconButton(onClick = onBackClick) {
						Icon(Icons.Default.ArrowBack, contentDescription = "뒤로 가기")
					}
				},
				actions = {
					TextButton(
						onClick = { onSaveClick(title, scheduleDtm,memo) },
						enabled = title.isNotBlank()
					) {
						Text("저장", fontSize = 16.sp, fontWeight = FontWeight.Bold)
					}
				},
				colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
			)
		},
		containerColor = Color.White
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
				.padding(horizontal = 16.dp)
				.verticalScroll(rememberScrollState())
		) {
			Spacer(modifier = Modifier.height(16.dp))

			OutlinedTextField(
				value = title,
				onValueChange = { title = it },
				placeholder = { Text("제목", fontSize = 20.sp) },
				modifier = Modifier.fillMaxWidth(),
				singleLine = true,
				colors = OutlinedTextFieldDefaults.colors(
					focusedBorderColor = MaterialTheme.colorScheme.primary,
					unfocusedBorderColor = Color.LightGray
				),
				textStyle = LocalTextStyle.current.copy(fontSize = 20.sp)
			)

			Spacer(modifier = Modifier.height(32.dp))
			HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
			Spacer(modifier = Modifier.height(16.dp))

			OutlinedTextField(
				value = memo,
				onValueChange = { memo = it },
				placeholder = { Text("메모를 입력하세요") },
				modifier = Modifier.fillMaxWidth().height(150.dp),
				singleLine = false,
				colors = OutlinedTextFieldDefaults.colors(
					focusedBorderColor = MaterialTheme.colorScheme.primary,
					unfocusedBorderColor = Color.LightGray
				)
			)
		}
	}
}