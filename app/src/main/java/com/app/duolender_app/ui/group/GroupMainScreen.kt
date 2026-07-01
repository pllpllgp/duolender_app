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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.duolender_app.data.group.response.GroupResponse
import com.app.duolender_app.ui.AppViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupMainScreen(
	onNavigateToCreate: () -> Unit,
) {
	var context = LocalContext.current
	var viewModel: GroupViewModel = viewModel(factory = AppViewModelFactory(context))
	var groupList = viewModel.groupList.collectAsState()

	var searchGroup by remember { mutableStateOf("") }
	var selectedGroupForDialog by remember { mutableStateOf<GroupResponse?>(null) }

	LaunchedEffect(searchGroup) {
		viewModel.loadGroupList(searchGroup)
	}

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
				value = searchGroup,
				onValueChange = { searchGroup = it },
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
				items(groupList.value) { group ->
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
	group: GroupResponse,
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
					text = group.groupNm,
					style = MaterialTheme.typography.titleMedium
				)
				Spacer(modifier = Modifier.height(4.dp))
				Text(
					text = group.groupMemo,
					style = MaterialTheme.typography.bodyMedium
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
	group: GroupResponse,
	onDismiss: () -> Unit,
	onJoinRequest: () -> Unit
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		title = {
			Text(text = group.groupNm, style = MaterialTheme.typography.headlineSmall)
		},
		text = {
			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(12.dp)
			) {
				Column {
					Text(text = "그룹 내용", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
					Text(text = group.groupMemo, style = MaterialTheme.typography.bodyLarge)
				}

				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					Column(modifier = Modifier.weight(1f)) {
						Text(text = "그룹장", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
						Text(text = group.groupCrtnId, style = MaterialTheme.typography.bodyMedium)
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