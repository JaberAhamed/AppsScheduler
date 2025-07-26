package com.sj.appscheduler.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sj.appscheduler.models.ScheduleAppInfo

@Database(entities = [ScheduleAppInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appInfoDao(): ScheduleDao
}
