package com.sj.appscheduler.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { it1 ->
            NotificationHelper.showBatteryNotification(context = it1, intent)
        }
    }
}
