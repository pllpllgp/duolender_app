package com.app.duolender_app.ui.group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.duolender_app.ui.AppViewModelFactory
import com.app.duolender_app.ui.schedule.ScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupRegisterScreen(
	onBack: () -> Unit,
	onSaveClick: (groupNm: String, groupMemo: String,) -> Unit,
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

	var groupName by remember { mutableStateOf("") }
	var groupMemo by remember { mutableStateOf("") }

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("그룹 생성", fontWeight = FontWeight.Bold) },
				navigationIcon = {
					IconButton(onClick = onBack) {
						Icon(Icons.Default.ArrowBack, contentDescription = null)
					}
				}
			)
		}
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
				.padding(horizontal = 24.dp)
				.verticalScroll(rememberScrollState()),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Spacer(modifier = Modifier.height(24.dp))

			OutlinedTextField(
				value = groupName,
				onValueChange = { groupName = it },
				label = { Text("그룹 이름") },
				modifier = Modifier.fillMaxWidth(),
				singleLine = true,
				shape = RoundedCornerShape(12.dp)
			)

			Spacer(modifier = Modifier.height(16.dp))

			OutlinedTextField(
				value = groupMemo,
				onValueChange = { groupMemo = it },
				label = { Text("그룹 소개") },
				modifier = Modifier
					.fillMaxWidth()
					.height(120.dp),
				shape = RoundedCornerShape(12.dp)
			)

			Spacer(modifier = Modifier.weight(1f))
			Spacer(modifier = Modifier.height(32.dp))

			Button(
				onClick = { onSaveClick(groupName, groupMemo) },
				modifier = Modifier
					.fillMaxWidth()
					.height(56.dp),
				shape = RoundedCornerShape(16.dp),
				enabled = groupName.isNotBlank()
			) {
				Text("그룹 만들기", fontSize = 18.sp, fontWeight = FontWeight.Bold)
			}

			Spacer(modifier = Modifier.height(24.dp))
		}
	}
}