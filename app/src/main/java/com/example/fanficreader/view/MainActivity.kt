package com.example.fanficreader.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.fanficreader.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createToolbar()
        if (savedInstanceState == null)
            createHomeScreen()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }
    //TODO: onStart, onResume, onPause, onStop, onDestroy not implemented yet
    //TODO: implement setProfileButton(), setSettingsButton(), and createSearchBar()

    /**
     * create the actionbar, the bar at the top of the screen that houses the setting/profile button
     * and name of the app
     */
    private fun createToolbar() {
        setSupportActionBar(findViewById(R.id.mainToolbar))
        supportActionBar?.setTitle(R.string.app_name)
        setProfileButton()
        setSettingsButton()
    }

    /**
     * create the home screen
     */
    private fun createHomeScreen() {
        supportFragmentManager.beginTransaction()
            .add(R.id.activityMainContainer, HomeScreenFragment(supportFragmentManager))
            .commit()
        createSearchBar()
    }

    /** YET TO BE IMPLEMENTED
     * onClick listener for the 'Profile' button; empty for now
     * eventually replace the text on the button with an image
     */
    fun setProfileButton() {
        /*var profileButton = findViewById<Button>(R.id.profileButton)
        profileButton.setBackgroundColor(Color.WHITE)*/
    }

    /** YET TO BE IMPLEMENTED
     * onClick listener for the 'Settings' button; empty for now
     * eventually replace the text on the button with an image
     */
    fun setSettingsButton() {
        /*var settingsButton = findViewById<Button>(R.id.settingsButton)
        settingsButton.setBackgroundColor(Color.WHITE)*/
    }

    /** YET TO BE IMPLEMENTED
     * create the search bar for users to filter stories by their desired category
     */
    private fun createSearchBar() {
        //empty for now
    }
}