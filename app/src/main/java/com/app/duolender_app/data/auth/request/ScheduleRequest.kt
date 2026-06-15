package com.app.duolender_app.data.auth.request

data class ScheduleRequest (
	val scheduleId: Int,
	val userId: String,
	val scheduleNm: String,
	val schduleDtm: String,
	val scheduleGroupId: String,
	val scheduleGroupNm: String,
	val scheduleColor: String,
)