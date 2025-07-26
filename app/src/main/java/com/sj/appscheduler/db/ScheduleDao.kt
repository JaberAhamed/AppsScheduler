package com.sj.appscheduler.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sj.appscheduler.models.AppSchedule
import com.sj.appscheduler.models.ScheduleAppInfo

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM scheduleappinfo")
    fun getAll(): List<ScheduleAppInfo>

    @Query("SELECT * FROM scheduleappinfo WHERE packageName = :packageName LIMIt 1")
    fun findByName(packageName: String): ScheduleAppInfo?

    @Query(
        """
    SELECT * FROM scheduleappinfo 
    WHERE hour = :currentHour AND minute = :currentMinute
    LIMIT 1
"""
    )
    fun findByTime(currentHour: Int, currentMinute: Int): ScheduleAppInfo?

    @Insert
    fun insertAll(vararg scheduleAppInfo: ScheduleAppInfo)

    @Delete
    fun delete(scheduleAppInfo: ScheduleAppInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppInfo(scheduleAppInfo: ScheduleAppInfo)

    @Update
    suspend fun updateAppInfo(scheduleAppInfo: ScheduleAppInfo): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScheduleApp(appSchedule: AppSchedule)

    @Update
    suspend fun updateAppSchedule(appSchedule: AppSchedule): Int

    @Delete
    fun delete(appSchedule: AppSchedule)

    @Query("SELECT * FROM appschedule WHERE packageName = :packageName LIMIt 1")
    fun getAppSchedule(packageName: String): AppSchedule?
}
