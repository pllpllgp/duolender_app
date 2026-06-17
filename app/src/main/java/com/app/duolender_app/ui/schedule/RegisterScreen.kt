package com.app.duolender_app.ui.schedule

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.duolender_app.ui.AppViewModelFactory
import com.app.duolender_app.ui.auth.AuthViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
	initialDate: String,
	onBackClick: () -> Unit,
	onSaveClick: (title: String, startDate: String, endDate: String, memo: String) -> Unit,
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

	val formatter = DateTimeFormatter.ofPattern("yyyy. M. d. (E)", Locale.KOREAN)
	val formattedInitialDate = remember(initialDate) {
		LocalDate.parse(initialDate).format(formatter)
	}

	var title by remember { mutableStateOf("") }
	var startDate by remember { mutableStateOf(formattedInitialDate) }
	var endDate by remember { mutableStateOf(formattedInitialDate) }
	var memo by remember { mutableStateOf("") }

	var showStartDatePicker by remember { mutableStateOf(false) }
	var showEndDatePicker by remember { mutableStateOf(false) }

	fun convertMillisToDateString(millis: Long): String {
		val date = Instant.ofEpochMilli(millis).atZone(ZoneId.of("UTC")).toLocalDate()
		val formatter = DateTimeFormatter.ofPattern("yyyy. M. d. (E)", Locale.KOREAN)
		return date.format(formatter)
	}

	val koreanConfig = remember {
		Configuration(context.resources.configuration).apply {
			setLocale(Locale.KOREAN)
		}
	}

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
						onClick = { onSaveClick(title, startDate, endDate, memo) },
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

			Spacer(modifier = Modifier.height(24.dp))

			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 8.dp),
				horizontalArrangement = Arrangement.SpaceAround
			) {
				Column(
					horizontalAlignment = Alignment.CenterHorizontally,
					modifier = Modifier.clickable { showStartDatePicker = true }
				) {
					Text(text = "시작일", fontSize = 12.sp, color = Color.Gray)
					Spacer(modifier = Modifier.height(4.dp))
					Text(text = startDate, fontSize = 16.sp)
				}

				// 💡 종료일 블록 (Column 사용)
				Column(
					horizontalAlignment = Alignment.CenterHorizontally,
					modifier = Modifier.clickable { showEndDatePicker = true }
				) {
					Text(text = "종료일", fontSize = 12.sp, color = Color.Gray)
					Spacer(modifier = Modifier.height(4.dp))
					Text(text = endDate, fontSize = 16.sp)
				}
			}

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

	if (showStartDatePicker) {
		CalendarDatePickerDialog(
			onDismiss = { showStartDatePicker = false },
			onConfirm = { millis -> startDate = convertMillisToDateString(millis) },
			koreanConfig = koreanConfig
		)
	}

	if (showEndDatePicker) {
		CalendarDatePickerDialog(
			onDismiss = { showEndDatePicker = false },
			onConfirm = { millis -> endDate = convertMillisToDateString(millis) },
			koreanConfig = koreanConfig
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDatePickerDialog(
	onDismiss: () -> Unit,
	onConfirm: (Long) -> Unit,
	koreanConfig: Configuration
) {
	val datePickerState = rememberDatePickerState()

	CompositionLocalProvider(LocalConfiguration provides koreanConfig) {
		DatePickerDialog(
			onDismissRequest = onDismiss,
			confirmButton = {
				TextButton(onClick = {
					datePickerState.selectedDateMillis?.let { millis ->
						onConfirm(millis)
					}
					onDismiss()
				}) { Text("확인") }
			},
			dismissButton = {
				TextButton(onClick = onDismiss) { Text("취소") }
			}
		) {
			DatePicker(state = datePickerState)
		}
	}
}