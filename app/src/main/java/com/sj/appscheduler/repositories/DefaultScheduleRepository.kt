package com.sj.appscheduler.repositories

import com.sj.appscheduler.db.ScheduleDao
import com.sj.appscheduler.models.AppSchedule
import com.sj.appscheduler.models.ScheduleAppInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultScheduleRepository @Inject constructor(

    private val scheduleDao: ScheduleDao
) : ScheduleRepository {

    override suspend fun insertAppInfo(scheduleAppInfo: ScheduleAppInfo) {
        scheduleDao.insertAppInfo(scheduleAppInfo = scheduleAppInfo)
    }

    override suspend fun getAllApps(): List<ScheduleAppInfo> = scheduleDao.getAll()

    override suspend fun updateAppInfo(scheduleAppInfo: ScheduleAppInfo) {
        scheduleDao.updateAppInfo(scheduleAppInfo = scheduleAppInfo)
    }

    override suspend fun deleteAppInfo(scheduleAppInfo: ScheduleAppInfo) {
        scheduleDao.delete(scheduleAppInfo = scheduleAppInfo)
    }

    override suspend fun getAppInfo(packageName: String): ScheduleAppInfo? = scheduleDao.findByName(packageName = packageName)

    // APP Schedule
    override suspend fun insertScheduleApp(appSchedule: AppSchedule) {
        scheduleDao.insertScheduleApp(appSchedule = appSchedule)
    }

    override suspend fun updateSchedule(appSchedule: AppSchedule) {
        scheduleDao.updateAppSchedule(appSchedule = appSchedule)
    }

    override suspend fun deleteAppSchedule(appSchedule: AppSchedule) {
        scheduleDao.delete(appSchedule = appSchedule)
    }

    override suspend fun getScheduleApp(packageName: String): AppSchedule? = scheduleDao.getAppSchedule(packageName = packageName)
}
