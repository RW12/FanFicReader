package com.example.fanficreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButton()
    }

    fun setButton() {
        val button = findViewById<Button>(R.id.activityMainButton)
        button.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.activityMainContainer,StoryViewerFragment()).commit()
        }
    }
}