package com.sj.appscheduler.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sj.appscheduler.ui.theme.AppSchedulerTheme
import java.util.Calendar

@Composable
fun ScheduleTimePicker(
    initialTimeMillis: Long? = null,
    onSave: (hour: Int, minutes: Int) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    var selectedHour by remember { mutableStateOf(calendar.get(Calendar.HOUR_OF_DAY)) }
    var selectedMinute by remember { mutableStateOf(calendar.get(Calendar.MINUTE)) }
    var showTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(initialTimeMillis) {
        initialTimeMillis?.let {
            calendar.timeInMillis = it
            selectedHour = calendar.get(Calendar.HOUR_OF_DAY)
            selectedMinute = calendar.get(Calendar.MINUTE)
        }
    }

    val timeText = remember(selectedHour, selectedMinute) {
        val isPM = selectedHour >= 12
        val hourForDisplay = if (selectedHour % 12 == 0) 12 else selectedHour % 12
        String.format("%02d:%02d %s", hourForDisplay, selectedMinute, if (isPM) "PM" else "AM")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selected Time: $timeText", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { showTimePicker = true }) {
            Text("Select Time")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                onSave(selectedHour, selectedMinute)
            }) {
                Text("Save")
            }

            Button(
                onClick = onCancel
            ) {
                Text("Cancel")
            }
        }
    }

    if (showTimePicker) {
        ShowTimePickerDialog(
            context = context,
            initialHour = selectedHour,
            initialMinute = selectedMinute,
            onTimeSelected = { hour, minute ->
                selectedHour = hour
                selectedMinute = minute
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimePickerWithCalendarScreenPreview() {
    AppSchedulerTheme {
        ScheduleTimePicker(
            initialTimeMillis = null,
            onSave = { hour, minutes -> Log.d("TAG", "Saved at: ") },
            onCancel = { Log.d("TAG", "Cancelled") }
        )
    }
}
