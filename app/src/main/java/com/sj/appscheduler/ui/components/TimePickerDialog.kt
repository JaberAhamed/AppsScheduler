package com.sj.appscheduler.ui.components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
fun ShowTimePickerDialog(
    context: Context,
    initialHour: Int,
    initialMinute: Int,
    onTimeSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    DisposableEffect(Unit) {
        val picker = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                onTimeSelected(hourOfDay, minute)
            },
            initialHour,
            initialMinute,
            false
        )
        picker.setOnCancelListener { onDismiss() }
        picker.show()

        onDispose { picker.dismiss() }
    }
}
