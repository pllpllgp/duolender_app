package com.app.duolender_app.ui.group

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(
	onNavigateToCreate: () -> Unit,
) {
	var searchQuery by remember { mutableStateOf("") }
	var selectedGroupForDialog by remember { mutableStateOf<GroupItem?>(null) }

	val dummyGroups = listOf(
		GroupItem("러닝 크루", "매주 주말 아침 한강 러닝", 15, "김러닝"),
		GroupItem("다이어트 챌린지", "한 달 3kg 감량 목표", 42, "이건강"),
		GroupItem("매일 헬스 인증", "꾸준한 웨이트 트레이닝 기록", 8, "박근육"),
		GroupItem("식단 공유방", "건강한 식단 사진 공유", 120, "최식단")
	)

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("그룹 검색") }
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = { onNavigateToCreate() },
				containerColor = MaterialTheme.colorScheme.primary,
				contentColor = MaterialTheme.colorScheme.onPrimary
			) {
				Icon(imageVector = Icons.Default.Add, contentDescription = "그룹 생성")
			}
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
				.padding(horizontal = 16.dp)
		) {
			OutlinedTextField(
				value = searchQuery,
				onValueChange = { searchQuery = it },
				modifier = Modifier.fillMaxWidth(),
				placeholder = { Text("그룹명 검색") },
				leadingIcon = {
					Icon(imageVector = Icons.Default.Search, contentDescription = null)
				},
				singleLine = true
			)

			Spacer(modifier = Modifier.height(16.dp))

			LazyColumn(
				verticalArrangement = Arrangement.spacedBy(8.dp),
				contentPadding = PaddingValues(bottom = 80.dp)
			) {
				items(dummyGroups) { group ->
					GroupCard(
						group = group,
						onCardClick = { selectedGroupForDialog = group }
					)
				}
			}
		}
	}

	selectedGroupForDialog?.let { group ->
		GroupDetailDialog(
			group = group,
			onDismiss = { selectedGroupForDialog = null },
			onJoinRequest = {
				selectedGroupForDialog = null
			}
		)
	}
}

@Composable
fun GroupCard(
	group: GroupItem,
	onCardClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onCardClick() },
		elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Column(modifier = Modifier.weight(1f)) {
				Text(
					text = group.name,
					style = MaterialTheme.typography.titleMedium
				)
				Spacer(modifier = Modifier.height(4.dp))
				Text(
					text = group.description,
					style = MaterialTheme.typography.bodyMedium
				)
				Spacer(modifier = Modifier.height(4.dp))
				Text(
					text = "멤버: ${group.memberCount}명",
					style = MaterialTheme.typography.labelMedium
				)
			}

			Button(
				onClick = onCardClick,
				modifier = Modifier.padding(start = 16.dp)
			) {
				Text("상세")
			}
		}
	}
}

@Composable
fun GroupDetailDialog(
	group: GroupItem,
	onDismiss: () -> Unit,
	onJoinRequest: () -> Unit
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		title = {
			Text(text = group.name, style = MaterialTheme.typography.headlineSmall)
		},
		text = {
			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(12.dp)
			) {
				Column {
					Text(text = "그룹 내용", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
					Text(text = group.description, style = MaterialTheme.typography.bodyLarge)
				}

				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					Column(modifier = Modifier.weight(1f)) {
						Text(text = "그룹장", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
						Text(text = group.leaderName, style = MaterialTheme.typography.bodyMedium)
					}
					Column(modifier = Modifier.weight(1f)) {
						Text(text = "그룹원 수", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
						Text(text = "${group.memberCount}명", style = MaterialTheme.typography.bodyMedium)
					}
				}
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("취소")
			}
		},
		confirmButton = {
			Button(onClick = onJoinRequest) {
				Text("가입신청")
			}
		}
	)
}

data class GroupItem(
	val name: String,
	val description: String,
	val memberCount: Int,
	val leaderName: String
)