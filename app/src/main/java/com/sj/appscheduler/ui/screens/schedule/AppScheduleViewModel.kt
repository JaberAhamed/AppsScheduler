package com.sj.appscheduler.ui.screens.schedule

import androidx.lifecycle.ViewModel
import com.sj.appscheduler.models.ScheduleAppInfo
import com.sj.appscheduler.repositories.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class AppScheduleUiState(
    val appScheduleAppInfo: ScheduleAppInfo? = null,
    val message: String? = null
)

@HiltViewModel
class AppScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel()
