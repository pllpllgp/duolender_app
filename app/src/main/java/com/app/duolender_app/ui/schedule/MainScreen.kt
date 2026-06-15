package com.app.duolender_app.ui.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.duolender_app.data.main.Schedule
import com.app.duolender_app.ui.AppViewModelFactory
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
	onNavigateToRegister: () -> Unit,
) {
	var currentMonth by remember { mutableStateOf(YearMonth.now()) }
	var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
	var showBottomSheet by remember { mutableStateOf(false) }

	val mockSchedules = mapOf(
		LocalDate.of(2026, 6, 18) to listOf(Schedule(1, "자웅군", "오전 8:00 - 오전 9:00")),
		LocalDate.of(2026, 6, 20) to listOf(Schedule(2, "은숙이 오프", "종일")),
		LocalDate.of(2026, 6, 25) to listOf(Schedule(3, "6.25 전쟁일", "기념일", 0xFFBDBDBD))
	)

	Scaffold(
		topBar = {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(vertical = 16.dp, horizontal = 8.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
					Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "이전 달")
				}
				Text(
					text = "${currentMonth.monthValue}월",
					fontSize = 24.sp,
					fontWeight = FontWeight.Bold
				)
				IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
					Icon(Icons.Default.KeyboardArrowRight, contentDescription = "다음 달")
				}
			}
		}
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
				.padding(horizontal = 8.dp)
		) {
			val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")
			Row(modifier = Modifier.fillMaxWidth()) {
				daysOfWeek.forEachIndexed { index, day ->
					Text(
						text = day,
						modifier = Modifier.weight(1f),
						textAlign = TextAlign.Center,
						color = if (index == 0) Color.Red else if (index == 6) Color.Blue else Color.Black,
						fontSize = 14.sp
					)
				}
			}

			Spacer(modifier = Modifier.height(8.dp))

			val daysInMonth = currentMonth.lengthOfMonth()
			val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value % 7

			LazyVerticalGrid(
				columns = GridCells.Fixed(7),
				modifier = Modifier.fillMaxSize()
			) {
				items(firstDayOfMonth) {
					Box(modifier = Modifier.aspectRatio(0.6f))
				}

				items(daysInMonth) { day ->
					val date = currentMonth.atDay(day + 1)
					val isSunday = date.dayOfWeek.value == 7
					val isSaturday = date.dayOfWeek.value == 6
					val daySchedules = mockSchedules[date] ?: emptyList()

					Column(
						modifier = Modifier
							.aspectRatio(0.6f)
							.clickable {
								selectedDate = date
								showBottomSheet = true
							}
							.padding(2.dp),
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text(
							text = "${day + 1}",
							color = if (isSunday) Color.Red else if (isSaturday) Color.Blue else Color.Black,
							fontWeight = FontWeight.Medium
						)

						Spacer(modifier = Modifier.height(4.dp))

						daySchedules.forEach { schedule ->
							Text(
								text = schedule.title,
								fontSize = 10.sp,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis,
								modifier = Modifier
									.fillMaxWidth()
									.background(Color(schedule.colorCode).copy(alpha = 0.2f), RoundedCornerShape(4.dp))
									.padding(horizontal = 2.dp, vertical = 1.dp),
								textAlign = TextAlign.Center
							)
						}
					}
				}
			}
		}
	}

	if (showBottomSheet && selectedDate != null) {
		val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
		val dayOfWeekStr = selectedDate!!.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)
		val schedulesForDay = mockSchedules[selectedDate] ?: emptyList()

		ModalBottomSheet(
			onDismissRequest = { showBottomSheet = false },
			sheetState = bottomSheetState,
			containerColor = Color.White
		) {
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(24.dp)
			) {
				Text(
					text = "${selectedDate!!.dayOfMonth} $dayOfWeekStr",
					fontSize = 24.sp,
					fontWeight = FontWeight.Bold
				)

				Spacer(modifier = Modifier.height(24.dp))

				if (schedulesForDay.isEmpty()) {
					Text("일정이 없습니다.", color = Color.Gray, modifier = Modifier.padding(vertical = 16.dp))
				} else {
					schedulesForDay.forEach { schedule ->
						Row(
							modifier = Modifier
								.fillMaxWidth()
								.padding(vertical = 8.dp),
							verticalAlignment = Alignment.CenterVertically
						) {
							Box(
								modifier = Modifier
									.width(4.dp)
									.height(40.dp)
									.background(Color(schedule.colorCode), RoundedCornerShape(2.dp))
							)
							Spacer(modifier = Modifier.width(16.dp))
							Column {
								Text(text = schedule.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
								Text(text = schedule.time, fontSize = 14.sp, color = Color.Gray)
							}
						}
					}
				}

				Spacer(modifier = Modifier.height(32.dp))

				Button(
					onClick = { onNavigateToRegister() },
					modifier = Modifier
						.fillMaxWidth()
						.height(56.dp),
					colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F3F5)),
					shape = RoundedCornerShape(28.dp)
				) {
					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically
					) {
						Text("${selectedDate!!.monthValue}월 ${selectedDate!!.dayOfMonth}일에 추가", color = Color.Black, fontSize = 16.sp)
						Icon(Icons.Default.Add, contentDescription = "추가", tint = Color.Black)
					}
				}
				Spacer(modifier = Modifier.height(24.dp))
			}
		}
	}
}