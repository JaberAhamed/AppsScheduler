package com.sj.appscheduler.ui.screens.schedule

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sj.appscheduler.ui.components.ScheduleTimePicker
import com.sj.appscheduler.ui.theme.AppSchedulerTheme
import com.sj.appscheduler.utils.SelectedAppState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    viewModel: AppScheduleViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.value.message) {
        Toast.makeText(context, state.value.message, Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(Unit) {
        checkAndRequestExactAlarmPermission(context)
        viewModel.initializeSchedular(context)
        viewModel.getSelectedAppScheduler(SelectedAppState.currentSelectedScheduleAppInfo?.packageName ?: "")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(SelectedAppState.currentSelectedScheduleAppInfo?.name ?: "") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    Icon(
                        modifier = Modifier.clickable {
                            onNavigateBack()
                        },
                        imageVector = androidx.compose.material.icons.Icons.Default.ArrowBackIosNew,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            ScheduleTimePicker(
                initialTimeMillis = null,
                onSave = { hour, minutes ->
                    viewModel.saveSchedule(
                        name = SelectedAppState.currentSelectedScheduleAppInfo?.name ?: "",
                        packageName = SelectedAppState.currentSelectedScheduleAppInfo?.packageName ?: "",
                        hour = hour,
                        minutes = minutes
                    )
                },
                onCancel = {
                    viewModel.deleteAppInfo(packageName = SelectedAppState.currentSelectedScheduleAppInfo?.packageName ?: "")
                }

            )
        }
    }
}

fun checkAndRequestExactAlarmPermission(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            intent.data = Uri.parse("package:" + context.packageName)
            context.startActivity(intent)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleScreenPreview() {
    AppSchedulerTheme {
        ScheduleScreen {
        }
    }
}
