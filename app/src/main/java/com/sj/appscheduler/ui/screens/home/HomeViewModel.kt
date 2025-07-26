package com.sj.appscheduler.ui.screens.home

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sj.appscheduler.models.AppInfoUiModel
import com.sj.appscheduler.models.ScheduleAppInfo
import com.sj.appscheduler.repositories.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val scheduleRepository: ScheduleRepository) : ViewModel() {

    private val _apps = MutableStateFlow<List<AppInfoUiModel>>(emptyList())
    val apps = _apps.asStateFlow()

    private var scheduleApps = emptyList<ScheduleAppInfo>()

    fun loadInstalledApps(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            scheduleApps = scheduleRepository.getAllApps()
            val pm: PackageManager = context.packageManager
            val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }

            val appInfoUiModels = pm.queryIntentActivities(mainIntent, 0)
                .mapNotNull { resolveInfo ->
                    // Filter out our own app
                    if (resolveInfo.activityInfo.packageName == context.packageName) {
                        return@mapNotNull null
                    }

                    val scheduledApp = scheduleApps.find { it.packageName == resolveInfo.activityInfo.packageName }
                    AppInfoUiModel(
                        name = resolveInfo.loadLabel(pm).toString(),
                        packageName = resolveInfo.activityInfo.packageName,
                        icon = resolveInfo.loadIcon(pm),
                        time = scheduledApp?.let {
                            var ampm = ""
                            ampm = if (it.hour <= 12) {
                                "AM"
                            } else {
                                "PM"
                            }
                            "${it.hour}:${it.minute} $ampm"
                        } ?: ""
                    )
                }
                .sortedBy { it.name.lowercase() }

            _apps.value = appInfoUiModels
        }
    }
}
