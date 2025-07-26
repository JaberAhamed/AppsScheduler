package com.sj.appscheduler.ui.screens.launch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Retrieve the dynamic package name from the intent
        val packageName = intent.getStringExtra("EXTRA_PACKAGE_NAME")

        // 2. Check if the package name exists
        if (!packageName.isNullOrEmpty()) {
            val launchIntent = packageManager.getLaunchIntentForPackage(packageName)

            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(launchIntent)
            } else {
                Toast.makeText(this, "App not found.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Error: App to launch was not specified.", Toast.LENGTH_LONG).show()
        }

        finish()
    }
}
