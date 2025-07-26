package com.sj.appscheduler.models

import android.graphics.drawable.Drawable

data class AppInfoUiModel(
    val name: String,
    val packageName: String,
    val icon: Drawable,
    val time: String? = ""
)
