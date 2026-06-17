package com.app.duolender_app.data.auth.request

data class ScheduleRequest (
	val scheduleId: Int? = null,
	val userId: String? = null,
	val scheduleNm: String? = null,
	val scheduleDtm: String? = null,
	val scheduleMemo: String? = null,
	val scheduleGroupId: String? = null,
	val scheduleGroupNm: String? = null,
	val scheduleColor: String? = null,
	val scheduleCrtnId:  String? = null,
	val scheduleCrtnDtm:  String? = null,
	val scheduleChngId:  String? = null,
	val scheduleChngDtm:  String? = null,

	val schScheduleDate: String? = null,
)