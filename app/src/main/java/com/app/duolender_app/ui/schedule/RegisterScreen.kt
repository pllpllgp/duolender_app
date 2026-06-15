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
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
	onBackClick: () -> Unit,
	onSaveClick: (title: String, isAllDay: Boolean, startDate: String, startTime: String, endDate: String, endTime: String, memo: String) -> Unit
) {
	var title by remember { mutableStateOf("") }
	var isAllDay by remember { mutableStateOf(false) }
	var startDate by remember { mutableStateOf("2026. 6. 18. (목)") }
	var startTime by remember { mutableStateOf("오전 8:00") }
	var endDate by remember { mutableStateOf("2026. 6. 18. (목)") }
	var endTime by remember { mutableStateOf("오전 9:00") }
	var memo by remember { mutableStateOf("") }

	// 💡 달력 다이얼로그를 띄울지 말지 결정하는 상태값
	var showStartDatePicker by remember { mutableStateOf(false) }
	var showEndDatePicker by remember { mutableStateOf(false) }

	// 💡 선택된 밀리초(Millis)를 "YYYY. M. d. (E)" 형태의 문자열로 바꿔주는 마법의 함수
	fun convertMillisToDateString(millis: Long): String {
		// DatePicker는 UTC 기준으로 밀리초를 반환하므로 UTC Zone으로 변환해 주어야 날짜가 틀어지지 않습니다.
		val date = Instant.ofEpochMilli(millis).atZone(ZoneId.of("UTC")).toLocalDate()
		val formatter = DateTimeFormatter.ofPattern("yyyy. M. d. (E)", Locale.KOREAN)
		return date.format(formatter)
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
						onClick = { onSaveClick(title, isAllDay, startDate, startTime, endDate, endTime, memo) },
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
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text("하루 종일", fontSize = 16.sp)
				Switch(checked = isAllDay, onCheckedChange = { isAllDay = it })
			}

			Spacer(modifier = Modifier.height(16.dp))

			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = startDate,
					fontSize = 16.sp,
					modifier = Modifier.clickable { showStartDatePicker = true } // 💡 클릭 시 달력 열기
				)
				if (!isAllDay) {
					Text(
						text = startTime,
						fontSize = 16.sp,
						modifier = Modifier.clickable { /* TODO: TimePicker 연동 예정 */ }
					)
				}
			}

			Spacer(modifier = Modifier.height(16.dp))

			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = endDate,
					fontSize = 16.sp,
					modifier = Modifier.clickable { showEndDatePicker = true } // 💡 클릭 시 달력 열기
				)
				if (!isAllDay) {
					Text(
						text = endTime,
						fontSize = 16.sp,
						modifier = Modifier.clickable { /* TODO: TimePicker 연동 예정 */ }
					)
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
				colors = OutlinedTextFieldDefaults.colors(
					focusedBorderColor = Color.Transparent,
					unfocusedBorderColor = Color.Transparent
				)
			)
		}
	}

	if (showStartDatePicker) {
		val datePickerState = rememberDatePickerState()

		val context = LocalContext.current
		val koreanConfig = Configuration(context.resources.configuration).apply {
			setLocale(Locale.KOREAN)
		}

		CompositionLocalProvider(LocalConfiguration provides koreanConfig) {
			DatePickerDialog(
				onDismissRequest = { showStartDatePicker = false },
				confirmButton = {
					TextButton(onClick = {
						datePickerState.selectedDateMillis?.let { millis ->
							startDate = convertMillisToDateString(millis)
						}
						showStartDatePicker = false
					}) { Text("확인") }
				},
				dismissButton = {
					TextButton(onClick = { showStartDatePicker = false }) { Text("취소") }
				}
			) {
				DatePicker(state = datePickerState)
			}
		}
	}

	if (showEndDatePicker) {
		val datePickerState = rememberDatePickerState()
		DatePickerDialog(
			onDismissRequest = { showEndDatePicker = false },
			confirmButton = {
				TextButton(onClick = {
					datePickerState.selectedDateMillis?.let { millis ->
						endDate = convertMillisToDateString(millis)
					}
					showEndDatePicker = false
				}) { Text("확인") }
			},
			dismissButton = {
				TextButton(onClick = { showEndDatePicker = false }) { Text("취소") }
			}
		) {
			DatePicker(state = datePickerState)
		}
	}
}