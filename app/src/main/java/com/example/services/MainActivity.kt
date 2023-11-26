package com.example.services

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import android.Manifest

class MainActivity : AppCompatActivity() {
    lateinit var start: Button
    lateinit var stop: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start = findViewById(R.id.start)
        stop = findViewById(R.id.stop)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0
            )
        }
        start.setOnClickListener {

            var intent = Intent(this, MyService::class.java)
            startService(intent)

        }


        stop.setOnClickListener {
            var intent = Intent(this, MyService::class.java)
            stopService(intent)
        }


    }
}