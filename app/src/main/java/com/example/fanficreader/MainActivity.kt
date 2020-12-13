package com.example.fanficreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.mainToolbar))
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null)
            createHomeScreen()
    }
    //need onStart, onResume, onPause, onStop, onDestroy?
    fun createHomeScreen() {
        supportFragmentManager.beginTransaction().add(R.id.activityMainContainer,HomeScreen()).commit()
    }

    /**
     * onClick listener for the 'Profile' button; empty for now
     * eventually replace the text on the button with an image (in the xml file)?
     */
    fun setProfileButton() {

    }
    /**
     * onClick listener for the 'Settings' button; empty for now
     * eventually replace the text on the button with an image (in the xml file)?
     */
    fun setSettingsButton() {

    }
}