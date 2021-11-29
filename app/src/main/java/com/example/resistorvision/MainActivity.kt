package com.example.resistorvision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import org.opencv.android.OpenCVLoader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hoge = findViewById<Button>(R.id.main_goto_camera)
        hoge.setOnClickListener{
            val intent = Intent(applicationContext, ResistorScanActivity::class.java)
            startActivity(intent)
        }
    }
}