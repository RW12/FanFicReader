package com.example.fanficreader.view

import com.example.fanficreader.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler


class SplashActivity: Activity() {
    var handler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler()
        handler!!.postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}