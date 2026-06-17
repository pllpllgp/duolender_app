package com.app.duolender_app.data.auth.response

data class ScheduleResponse(
	val scheduleId: Int,
	val userId: String,
	val scheduleNm: String,
	val scheduleDtm: String,
	val scheduleMemo: String,
	val scheduleGroupId: String? = null,
	val scheduleGroupNm: String? = null,
	val scheduleColor: String? = null,
)