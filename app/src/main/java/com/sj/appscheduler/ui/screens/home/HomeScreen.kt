package com.sj.appscheduler.ui.screens.home // HomeScreen.kt
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sj.appscheduler.models.AppInfoUiModel
import com.sj.appscheduler.ui.components.AppItemCard
import com.sj.appscheduler.ui.components.ScheduleDialog
import com.sj.appscheduler.ui.theme.AppSchedulerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val apps by viewModel.apps.collectAsState()

    // State for managing the dialog
    var showDialog by remember { mutableStateOf(false) }
    var selectedApp by remember { mutableStateOf<AppInfoUiModel?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadInstalledApps(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Scheduler") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        if (apps.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )

            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(apps) { app ->
                    AppItemCard(
                        appInfo = app,
                        onScheduleClick = {
                            selectedApp = app
                            showDialog = true
                        }
                    )
                }
            }
        }

        if (showDialog && selectedApp != null) {
            ScheduleDialog(
                appName = selectedApp!!.name,
                onDismiss = { showDialog = false },
                onSave = { scheduleType, timeValue ->
                    // --- Here you would save the schedule ---
                    // For now, just show a Toast message
                    val message = "Saved: ${selectedApp!!.name} - $timeValue " +
                        if (scheduleType == "Weekly") "hours/week" else "mins/hour"

                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()

                    // TODO: Implement actual saving logic (e.g., to SharedPreferences or a Room database)

                    showDialog = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppSchedulerTheme {
        HomeScreen(
            viewModel = hiltViewModel()
        )
    }
}
