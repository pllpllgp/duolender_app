package com.app.duolender_app.data.auth.request

data class ScheduleRequest (
	val scheduleId: Int? = null,
	val userId: String? = null,
	val scheduleNm: String? = null,
	val scheduleStartDtm: String? = null,
	val scheduleEndDtm: String? = null,
	val scheduleMemo: String? = null,
	val scheduleGroupId: String? = null,
	val scheduleGroupNm: String? = null,
	val scheduleColor: String? = null,

	val schScheduleDate: String? = null,
)