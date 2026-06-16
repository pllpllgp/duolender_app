package com.app.duolender_app.data.auth.request

data class ScheduleRequest (
	val scheduleId: Int,
	val userId: String,
	val scheduleNm: String,
	val schduleStartDtm: String,
	val schduleEndDtm: String,
	val scheduleGroupId: String? = null,
	val scheduleGroupNm: String? = null,
	val scheduleColor: String? = null,
)