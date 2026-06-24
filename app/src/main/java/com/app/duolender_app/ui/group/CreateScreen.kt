package com.app.duolender_app.ui.group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(onBack: () -> Unit) {
	var groupName by remember { mutableStateOf("") }
	var groupDescription by remember { mutableStateOf("") }
	var selectedCategory by remember { mutableStateOf("운동") }
	var isPublic by remember { mutableStateOf(true) }

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
				value = groupDescription,
				onValueChange = { groupDescription = it },
				label = { Text("그룹 소개") },
				modifier = Modifier
					.fillMaxWidth()
					.height(120.dp),
				shape = RoundedCornerShape(12.dp)
			)

			Spacer(modifier = Modifier.height(24.dp))

			Text(
				"카테고리 선택",
				modifier = Modifier.fillMaxWidth(),
				style = MaterialTheme.typography.titleSmall,
				fontWeight = FontWeight.SemiBold
			)

			Spacer(modifier = Modifier.height(24.dp))

			Row(
				modifier = Modifier
					.fillMaxWidth()
					.clip(RoundedCornerShape(12.dp))
					.background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
					.padding(16.dp),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Column {
					Text("공개 그룹 설정", fontWeight = FontWeight.Bold)
					Text(if (isPublic) { "누구나 검색하고 가입할 수 있습니다"}
								else { "초대된 인원만 가입할 수 있습니다"},
						style = MaterialTheme.typography.bodySmall
					)
				}
				Switch(checked = isPublic, onCheckedChange = { isPublic = it })
			}

			Spacer(modifier = Modifier.weight(1f))
			Spacer(modifier = Modifier.height(32.dp))

			Button(
				onClick = { },
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