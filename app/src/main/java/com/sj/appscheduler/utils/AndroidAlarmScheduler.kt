package com.sj.appscheduler.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.sj.appscheduler.models.ScheduleAppInfo
import java.util.Calendar

class AndroidAlarmScheduler(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: ScheduleAppInfo, hashCode: (Int) -> Unit) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_PACKAGE_NAME", item.packageName)
        }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, item.hour)
            set(Calendar.MINUTE, item.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val triggerAtMillis = calendar.timeInMillis
        val code = item.hashCode()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            PendingIntent.getBroadcast(
                context,
                code,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )

        hashCode(code)
    }

    override fun cancel(code: Int) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                code,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}
