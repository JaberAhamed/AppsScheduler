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
}
