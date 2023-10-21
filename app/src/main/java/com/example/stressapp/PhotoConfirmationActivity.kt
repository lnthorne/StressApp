package com.example.stressapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class PhotoConfirmationActivity : AppCompatActivity() {
    private lateinit var image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_confirmation)

        image = findViewById(R.id.ivImage)

        val selectedImageResource = intent.getIntExtra(IMAGE_RESOURCE_KEY, -1)
        val position = intent.getIntExtra(POSITION_KEY, -1)

        image.setImageResource(selectedImageResource)

    }

    companion object {
        const val IMAGE_RESOURCE_KEY = "image_resource_key"
        const val POSITION_KEY = "position_key"
    }
}