package com.sj.appscheduler.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sj.appscheduler.models.AppInfoUiModel

object SelectedAppState {
    var currentSelectedScheduleAppInfo by mutableStateOf<AppInfoUiModel?>(null)
}
