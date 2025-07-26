package com.sj.appscheduler.repositories

import com.sj.appscheduler.models.AppSchedule
import com.sj.appscheduler.models.ScheduleAppInfo

interface ScheduleRepository {
    suspend fun insertAppInfo(scheduleAppInfo: ScheduleAppInfo)

    suspend fun getAllApps(): List<ScheduleAppInfo>

    suspend fun updateAppInfo(scheduleAppInfo: ScheduleAppInfo)

    suspend fun deleteAppInfo(scheduleAppInfo: ScheduleAppInfo)

    suspend fun getAppInfo(packageName: String): ScheduleAppInfo?

    suspend fun insertScheduleApp(appSchedule: AppSchedule)

    suspend fun updateSchedule(appSchedule: AppSchedule)

    suspend fun deleteAppSchedule(appSchedule: AppSchedule)

    suspend fun getScheduleApp(packageName: String): AppSchedule?
}
