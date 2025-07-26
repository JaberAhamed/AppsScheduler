package com.sj.appscheduler.ui.screens.schedule

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sj.appscheduler.models.AppSchedule
import com.sj.appscheduler.models.ScheduleAppInfo
import com.sj.appscheduler.repositories.ScheduleRepository
import com.sj.appscheduler.utils.AndroidAlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class AppScheduleUiState(
    val appScheduleAppInfo: ScheduleAppInfo? = null,
    val message: String? = null
)

@HiltViewModel
class AppScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    var schedularAndroidAlarmScheduler: AndroidAlarmScheduler? = null
    private var scheduleAppInfo = MutableStateFlow<ScheduleAppInfo?>(null)
    private var message = MutableStateFlow("")

    private val _state = MutableStateFlow(AppScheduleUiState())
    val state = _state as StateFlow<AppScheduleUiState>

    init {
        viewModelScope.launch {
            combine(
                scheduleAppInfo,
                message
            ) { scheduleAppInfo, message ->
                AppScheduleUiState(
                    appScheduleAppInfo = scheduleAppInfo,
                    message = message
                )
            }
                .catch { throwable ->
                }
                .collect {
                    _state.value = it
                }
        }
    }

    fun saveSchedule(name: String, packageName: String, hour: Int, minutes: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val schedule = ScheduleAppInfo(
                name = name,
                packageName = packageName,
                hour = hour,
                isSetAlarm = true,
                minute = minutes
            )

            val existingSchedule = scheduleRepository.getAppInfo(packageName = packageName)
            existingSchedule?.let { t1 ->
                scheduleRepository.updateAppInfo(schedule)
            } ?: scheduleRepository.insertAppInfo(schedule)

            schedularAndroidAlarmScheduler?.schedule(schedule) { code ->
                insertScheduleApp(packageName, code)
            }

            message.value = "Set app schedule"
        }
    }

    fun deleteAppInfo(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingSchedule = scheduleRepository.getAppInfo(packageName = packageName)
            existingSchedule?.let { t1 ->
                scheduleRepository.deleteAppInfo(t1)
                deleteScheduleApp(packageName = packageName)
            }
            message.value = "Cancel app schedule"
        }
    }

    fun getSelectedAppScheduler(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            scheduleAppInfo.value = scheduleRepository.getAppInfo(packageName = packageName)
        }
    }

    fun initializeSchedular(context: Context) {
        schedularAndroidAlarmScheduler = AndroidAlarmScheduler(context = context)
    }

    fun insertScheduleApp(packageName: String, code: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val appSchedule = scheduleRepository.getScheduleApp(packageName = packageName)

            schedularAndroidAlarmScheduler?.cancel(appSchedule?.alarmCode ?: -1)

            appSchedule?.let {
                scheduleRepository.updateSchedule(
                    appSchedule = AppSchedule(
                        packageName = packageName,
                        alarmCode = code,
                        isSuccess = true
                    )
                )
            } ?: scheduleRepository.insertScheduleApp(
                appSchedule = AppSchedule(
                    packageName = packageName,
                    alarmCode = code,
                    isSuccess = true
                )
            )
        }
    }

    fun deleteScheduleApp(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val appSchedule = scheduleRepository.getScheduleApp(packageName = packageName)
            schedularAndroidAlarmScheduler?.cancel(appSchedule?.alarmCode ?: -1)
            appSchedule?.let {
                scheduleRepository.deleteAppSchedule(appSchedule)
                schedularAndroidAlarmScheduler?.cancel(appSchedule.alarmCode)
            }
        }
    }
}
