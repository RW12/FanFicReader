package com.example.fanficreader.view

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.fanficreader.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createToolbar()
        if(savedInstanceState == null)
            createHomeScreen()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar,menu)
        return true
    }
    //need onStart, onResume, onPause, onStop, onDestroy?

    //set up the toolbar
    private fun createToolbar() {
        setSupportActionBar(findViewById(R.id.mainToolbar))
        supportActionBar?.setTitle(R.string.app_name)
        //HANDLING CLICK EVENTS OF THE ITEMS IN THE MENU: https://developer.android.com/guide/topics/ui/menus "Handling click events"
        //setProfileButton()
        //setSettingsButton()
    }
    /**
     * onClick listener for the 'Profile' button; empty for now
     * eventually replace the text on the button with an image (in the xml file)?
     */
    fun setProfileButton() {
        var profileButton = findViewById<Button>(R.id.profileButton)
        profileButton.setBackgroundColor(Color.WHITE)
    }
    /**
     * onClick listener for the 'Settings' button; empty for now
     * eventually replace the text on the button with an image (in the xml file)?
     */
    fun setSettingsButton() {
        var settingsButton = findViewById<Button>(R.id.settingsButton)
        settingsButton.setBackgroundColor(Color.WHITE)
    }

    //create the home screen
    private fun createHomeScreen() {
        supportFragmentManager.beginTransaction().add(R.id.activityMainContainer, HomeScreenFragment(supportFragmentManager)).commit()
        //createSearchBar()
    }
    /*private fun createSearchBar() {
        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.menu_search).actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }
    }*/
}