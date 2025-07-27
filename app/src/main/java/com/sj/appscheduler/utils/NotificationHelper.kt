package com.sj.appscheduler.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.sj.appscheduler.R
import com.sj.appscheduler.ui.screens.launch.LaunchActivity

object NotificationHelper {
    fun showBatteryNotification(context: Context, intent: Intent?) {
        val packageName = intent?.getStringExtra("EXTRA_PACKAGE_NAME")

        if (packageName.isNullOrEmpty()) {
            Log.e("AlarmReceiver", "Package name is missing from intent extras.")
            return
        }

        val fullScreenIntent = Intent(context, LaunchActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("EXTRA_PACKAGE_NAME", packageName)
        }

        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val fullScreenPendingIntent = PendingIntent.getActivity(
            context,
            0,
            fullScreenIntent,
            pendingIntentFlags
        )

        val channelId = "app_channel"
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "App Open Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Open App")
            .setContentText("Open your schedule app")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setAutoCancel(true)
            .build()

        manager.notify(1001, notification)
    }
}
