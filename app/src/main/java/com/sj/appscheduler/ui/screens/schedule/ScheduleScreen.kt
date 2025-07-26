package com.sj.appscheduler.ui.screens.schedule

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(SelectedAppState.currentSelectedScheduleAppInfo?.name ?: "") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            ScheduleTimePicker(
                initialTimeMillis = null,
                onSave = { millis -> Log.d("TAG", "Saved at: $millis") },
                onCancel = { Log.d("TAG", "Cancelled") },
                onUpdate = { millis -> Log.d("TAG", "Updated to: $millis") }
            )
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
