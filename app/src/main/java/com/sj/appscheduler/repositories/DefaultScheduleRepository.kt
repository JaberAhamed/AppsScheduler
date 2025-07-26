package com.sj.appscheduler.repositories

import com.sj.appscheduler.db.ScheduleDao
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

    override suspend fun updateAppInfo(scheduleAppInfo: ScheduleAppInfo) {
        scheduleDao.updateAppInfo(scheduleAppInfo = scheduleAppInfo)
    }

    override suspend fun deleteAppInfo(scheduleAppInfo: ScheduleAppInfo) {
        scheduleDao.delete(scheduleAppInfo = scheduleAppInfo)
    }

    override suspend fun getAppInfo(packageName: String): ScheduleAppInfo = scheduleDao.findByName(packageName = packageName)
}
