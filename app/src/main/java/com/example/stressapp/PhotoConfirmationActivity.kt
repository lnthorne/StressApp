package com.example.stressapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import java.sql.Timestamp
import kotlin.system.exitProcess

class PhotoConfirmationActivity : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var cancelBtn: Button
    private lateinit var submitBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_confirmation)

        image = findViewById(R.id.ivImage)
        cancelBtn = findViewById(R.id.btnCancel)
        submitBtn = findViewById(R.id.btnSubmit)

        val selectedImageResource = intent.getIntExtra(IMAGE_RESOURCE_KEY, -1)
        val position = intent.getIntExtra(POSITION_KEY, -1)
        val timestamp = intent.getLongExtra(TIMESTAMP_KEY, -1)
        val stressData = StressData(timestamp, position)

        image.setImageResource(selectedImageResource)

        cancelBtn.setOnClickListener() {
            finish()
        }

        submitBtn.setOnClickListener() {
            StressDataController.addEntry(this, stressData)
            finishAffinity()
            exitProcess(0)
        }

    }

    companion object {
        const val IMAGE_RESOURCE_KEY = "image_resource_key"
        const val POSITION_KEY = "position_key"
        const val TIMESTAMP_KEY = "Timestamp"
    }
}