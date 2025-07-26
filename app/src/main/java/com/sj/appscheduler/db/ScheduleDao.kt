package com.sj.appscheduler.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sj.appscheduler.models.ScheduleAppInfo

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM scheduleappinfo")
    fun getAll(): List<ScheduleAppInfo>

    @Query("SELECT * FROM scheduleappinfo WHERE packageName = :packageName LIMIt 1")
    fun findByName(packageName: String): ScheduleAppInfo

    @Insert
    fun insertAll(vararg scheduleAppInfo: ScheduleAppInfo)

    @Delete
    fun delete(scheduleAppInfo: ScheduleAppInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppInfo(scheduleAppInfo: ScheduleAppInfo)

    @Update
    suspend fun updateAppInfo(scheduleAppInfo: ScheduleAppInfo): Int
}
