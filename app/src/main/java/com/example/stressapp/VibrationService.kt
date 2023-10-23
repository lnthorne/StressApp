package com.example.stressapp

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.Vibrator

class VibrationService : Service() {
    private var vibrator: Vibrator? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        vibrator = getSystemService(Vibrator::class.java)
        val pattern = longArrayOf(0, 500, 500)
        vibrator?.vibrate(pattern, 0)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        vibrator?.cancel()
    }
}