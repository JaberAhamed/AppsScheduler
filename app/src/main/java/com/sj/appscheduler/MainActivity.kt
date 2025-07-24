package com.sj.appscheduler

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.sj.appscheduler.ui.theme.AppSchedulerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppSchedulerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

    for (appInfo in installedApps) {
        val appName = packageManager.getApplicationLabel(appInfo).toString()
        val packageName = appInfo.packageName
        val icon = packageManager.getApplicationIcon(appInfo)

        Log.d("InstalledApp", "Name: $appName, Package: $packageName")
    }

    Column(modifier = Modifier) {

    }
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppSchedulerTheme {
        Greeting("Android")
    }
}