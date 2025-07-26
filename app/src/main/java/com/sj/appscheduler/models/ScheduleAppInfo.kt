package com.sj.appscheduler.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScheduleAppInfo(
    @ColumnInfo val name: String,
    @PrimaryKey val packageName: String,
    @ColumnInfo val isSetAlarm: Boolean,
    @ColumnInfo val hour: Int,
    @ColumnInfo val minute: Int
)
