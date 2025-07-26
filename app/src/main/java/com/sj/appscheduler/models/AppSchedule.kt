package com.sj.appscheduler.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppSchedule(
    @PrimaryKey val id: Int,
    @ColumnInfo val packageName: String,
    @ColumnInfo val isSuccess: String
)
