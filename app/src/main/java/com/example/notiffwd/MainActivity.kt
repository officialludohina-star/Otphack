package com.example.notiffwd

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 120, 50, 50)
        }

        val title = TextView(this).apply {
            text = "Flashlight 🔦"
            textSize = 26f
        }

        val info = TextView(this).apply {
            text = "\nNotification access ON karo taake app theek chale."
            textSize = 16f
        }

        val btn = Button(this).apply {
            text = "Enable Notification Access"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
            }
        }

        val statusBtn = Button(this).apply {
            text = "Check Status"
            setOnClickListener {
                val enabled = Settings.Secure.getString(
                    contentResolver,
                    "enabled_notification_listeners"
                )?.contains(packageName) == true

                info.text = if (enabled) "✅ ON hai — sab kaam kar raha hai"
                            else "❌ OFF hai — pehle ON karo"
            }
        }

        layout.addView(title)
        layout.addView(info)
        layout.addView(btn)
        layout.addView(statusBtn)
        setContentView(layout)
    }
}