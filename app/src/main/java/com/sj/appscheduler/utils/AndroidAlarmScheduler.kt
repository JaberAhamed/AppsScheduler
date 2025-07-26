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

        // Calculate the trigger time in milliseconds
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, item.hour)
            set(Calendar.MINUTE, item.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // If the calculated time is in the past, add a day to schedule for tomorrow
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val triggerAtMillis = calendar.timeInMillis

        // Use setRepeating or setExactAndAllowWhileIdle with a calculated next day trigger
        // For daily repeat, setRepeating is generally more appropriate if you don't need
        // the exact "RTC_WAKEUP" behavior for every single alarm instance immediately.
        // However, for precise daily alarms, you might reschedule in AlarmReceiver.
        // For simplicity and matching your initial setExactAndAllowWhileIdle,
        // we'll calculate the next day's alarm each time.
        val code = item.hashCode()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis, // Use the calculated time
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
