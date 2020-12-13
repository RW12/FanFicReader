package com.example.fanficreader

import androidx.fragment.app.Fragment

class HomeScreen : Fragment(R.layout.fragment_home_screen) {

    var searchBarText = "" //should be using the text (albeit blank) of the searchBar editText thing

    /*TO INCLDUE: search bar, listed stories (for now, represented with a container that can be filled later; make sure it's 'scrollable'
        At a minimum, this should consist of the following:
        - RecyclerView
        - App/Action Bar
        - BottomNavigationView
         */
    //onClick listener for searchBarButton: get text from the searchBar editText thing and store into searchBarText
    //editText when 'enter' is clicked from the keyboard, default to it being equivalent to pressing the 'search' button
}