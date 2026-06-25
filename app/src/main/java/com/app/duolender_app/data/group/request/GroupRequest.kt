package com.app.duolender_app.data.group.request

data class GroupRequest (
	val groupId: String? = null,
	val groupNm: String,
	val groupCrtnId: String,
	val groupMemo: String,
)