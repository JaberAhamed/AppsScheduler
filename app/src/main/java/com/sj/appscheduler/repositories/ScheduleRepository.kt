package com.sj.appscheduler.repositories

import com.sj.appscheduler.models.ScheduleAppInfo

interface ScheduleRepository {
    suspend fun insertAppInfo(scheduleAppInfo: ScheduleAppInfo)

    suspend fun updateAppInfo(scheduleAppInfo: ScheduleAppInfo)

    suspend fun deleteAppInfo(scheduleAppInfo: ScheduleAppInfo)

    suspend fun getAppInfo(packageName: String): ScheduleAppInfo
}
