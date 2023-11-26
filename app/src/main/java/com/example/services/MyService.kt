package com.example.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MyService : Service() {
    private var serviceId = 1
    private lateinit var mp: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mp = MediaPlayer.create(this, R.raw.song)
        mp.isLooping = true
//        mp.setOnCompletionListener {
//            // The song has completed, stop the service
//            stopSelf()
//        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!mp.isPlaying) {
            mp.start()
            // Send notification when MediaPlayer starts
            sendNotification("Song Started")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val channel_ID = "myChannel_ID"
            val channel = NotificationChannel(
                channel_ID, "Default", NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

            val notification = NotificationCompat.Builder(this, channel_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Playing Song")
                .setContentText("Enjoy the music!")
                .build()
            startForeground(serviceId, notification)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mp.isPlaying) {
            mp.stop()
            mp.release()
        }
    }

    private fun sendNotification(message: String) {
        val channel_ID = "myChannel_ID"
        val notification = NotificationCompat.Builder(this, channel_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("MediaPlayer Notification")
            .setContentText(message)
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}