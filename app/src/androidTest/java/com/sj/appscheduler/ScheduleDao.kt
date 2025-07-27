package com.sj.appscheduler

import androidx.room.Room
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.sj.appscheduler.db.AppDatabase
import com.sj.appscheduler.models.ScheduleAppInfo
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ScheduleDao {
    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertTaskAndGetById() = runTest {
        val schedule = ScheduleAppInfo(
            name = "Youtube",
            packageName = "com.google.content.youtube",
            isSetAlarm = true,
            hour = 12,
            minute = 12
        )
        database.appInfoDao().insertAppInfo(schedule)

        val loaded = database.appInfoDao().findByName(schedule.packageName)

        assertNotNull(loaded as ScheduleAppInfo)

        assertEquals(schedule.name, loaded.name)
        assertEquals(schedule.packageName, loaded.packageName)
        assertEquals(schedule.isSetAlarm, loaded.isSetAlarm)
        assertEquals(schedule.hour, loaded.hour)
        assertEquals(schedule.minute, loaded.minute)
    }

    @Test
    fun updateSchedule() = runTest {
        val schedule = ScheduleAppInfo(
            name = "YouTube",
            packageName = "com.google.content.youtube",
            isSetAlarm = true,
            hour = 10,
            minute = 30
        )
        database.appInfoDao().insertAppInfo(schedule)

        val updatedSchedule = schedule.copy(
            isSetAlarm = false,
            hour = 15,
            minute = 45
        )
        database.appInfoDao().updateAppInfo(updatedSchedule)

        val loaded = database.appInfoDao().findByName(schedule.packageName)

        assertNotNull(loaded)
        assertEquals(false, loaded?.isSetAlarm)
        assertEquals(15, loaded?.hour)
        assertEquals(45, loaded?.minute)
    }

    @Test
    fun deleteSchedule() = runTest {
        val schedule = ScheduleAppInfo(
            name = "YouTube",
            packageName = "com.google.content.youtube",
            isSetAlarm = true,
            hour = 9,
            minute = 0
        )
        database.appInfoDao().insertAppInfo(schedule)

        database.appInfoDao().delete(schedule)

        val loaded = database.appInfoDao().findByName(schedule.packageName)
        assertNull(loaded)
    }
}
