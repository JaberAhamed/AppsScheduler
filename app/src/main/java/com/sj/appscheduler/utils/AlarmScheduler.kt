package com.sj.appscheduler.utils

import com.sj.appscheduler.models.ScheduleAppInfo

interface AlarmScheduler {
    fun schedule(item: ScheduleAppInfo, hashCode: (Int) -> Unit)
    fun cancel(item: Int)
}
