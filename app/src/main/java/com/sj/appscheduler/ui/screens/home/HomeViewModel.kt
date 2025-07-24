package com.sj.appscheduler.ui.screens.home

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sj.appscheduler.models.AppInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _apps = MutableStateFlow<List<AppInfoUiModel>>(emptyList())
    val apps = _apps.asStateFlow()

    fun loadInstalledApps(context: Context) {
        viewModelScope.launch {
            val pm: PackageManager = context.packageManager
            val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }

            val appInfos = pm.queryIntentActivities(mainIntent, 0)
                .mapNotNull { resolveInfo ->
                    // Filter out our own app
                    if (resolveInfo.activityInfo.packageName == context.packageName) {
                        return@mapNotNull null
                    }

                    AppInfoUiModel(
                        name = resolveInfo.loadLabel(pm).toString(),
                        packageName = resolveInfo.activityInfo.packageName,
                        icon = resolveInfo.loadIcon(pm)
                    )
                }
                .sortedBy { it.name.lowercase() }

            _apps.value = appInfos
        }
    }
}
