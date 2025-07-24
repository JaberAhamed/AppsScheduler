package com.sj.appscheduler.ui.components // ScheduleDialog.kt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ScheduleDialog(
    appName: String,
    onDismiss: () -> Unit,
    onSave: (scheduleType: String, timeValue: Int) -> Unit
) {
    var scheduleType by remember { mutableStateOf("Weekly") } // "Weekly" or "Hourly"
    var timeValue by remember { mutableStateOf("5") } // Default value

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Schedule for $appName",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Schedule Type Radio Buttons
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = scheduleType == "Weekly",
                        onClick = { scheduleType = "Weekly" }
                    )
                    Text(
                        text = "Weekly",
                        modifier = Modifier.padding(start = 4.dp).clickable { scheduleType = "Weekly" }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(
                        selected = scheduleType == "Hourly",
                        onClick = { scheduleType = "Hourly" }
                    )
                    Text(
                        text = "Hourly",
                        modifier = Modifier.padding(start = 4.dp).clickable { scheduleType = "Hourly" }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Time Input
                OutlinedTextField(
                    value = timeValue,
                    onValueChange = { newValue ->
                        // Allow only digits
                        if (newValue.all { it.isDigit() }) {
                            timeValue = newValue
                        }
                    },
                    label = { Text(if (scheduleType == "Weekly") "Hours per week" else "Minutes per hour") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onSave(scheduleType, timeValue.toIntOrNull() ?: 0)
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}
