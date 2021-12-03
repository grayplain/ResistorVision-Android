package com.example.resistorvision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gotoCamera = findViewById<Button>(R.id.main_goto_camera)
        gotoCamera.setOnClickListener{
            val intent = Intent(applicationContext, ResistorScanActivity::class.java)
            startActivity(intent)
        }

        val gotoIntro = findViewById<Button>(R.id.main_goto_introduce)
        gotoIntro.setOnClickListener{
            val intent = Intent(applicationContext, IntroduceActivity::class.java)
            startActivity(intent)
        }
    }
}