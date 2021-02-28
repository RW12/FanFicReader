package com.example.fanficreader.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fanficreader.R
import com.example.fanficreader.view.recyclerview.StoryDetailsAdapter
import com.example.fanficreader.model.StoryDetailsData
import com.google.firebase.firestore.FirebaseFirestore

private const val TITLE_AND_AUTHOR_KEY = "titleAndAuthor"
private const val SUMMARY_KEY = "summary"
private const val TAGS_KEY = "tags"
class HomeScreenFragment(private val supportFragmentManager: FragmentManager) : Fragment(R.layout.fragment_home_screen) {
    var searchBarText = "" //should be using the text (albeit blank) of the searchBar editText thing
    var storyList = mutableListOf<StoryDetailsData>()
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
    }

    /**
     * setting up recycler view and adapter
     */
    fun initRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.listedStoryRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = StoryDetailsAdapter(supportFragmentManager)
        recyclerView.adapter = adapter
        adapter.submitList(listOf(StoryDetailsData(), StoryDetailsData("","", "", "")))
        addDetailsToCards(adapter)
    }
    /**
     * filling in the story cards in the adapter with data from firebase
     */
    fun addDetailsToCards(adapter: StoryDetailsAdapter) {
        //**find way to change using list to add stuff to adapater
        db.collection("action").document("Alex Rider").get()
                .addOnSuccessListener {
                    val authorText = it.getString(TITLE_AND_AUTHOR_KEY).orEmpty()
                    val summaryText = it.getString(SUMMARY_KEY).orEmpty()
                    storyList.add(StoryDetailsData(authorText,"tags", summaryText, "characters"))
                    addFantasyStory(adapter)
                }
    }
    fun addFantasyStory(adapter: StoryDetailsAdapter) {
        db.collection("fantasy").document("Percy Jackson and the Olympians").get()
                .addOnSuccessListener {
                    val authorText = it.getString(TITLE_AND_AUTHOR_KEY).orEmpty()
                    val summaryText = it.getString(SUMMARY_KEY).orEmpty()
                    storyList.add(StoryDetailsData(authorText,"tags", summaryText, "characters"))
                    addMysteryStory(adapter)
                }
    }
    fun addMysteryStory(adapter: StoryDetailsAdapter) {
        db.collection("mystery").document("Sherlock Holmes").get()
                .addOnSuccessListener {
                    val authorText = it.getString(TITLE_AND_AUTHOR_KEY).orEmpty()
                    val summaryText = it.getString(SUMMARY_KEY).orEmpty()
                    storyList.add(StoryDetailsData(authorText,"tags", summaryText, "characters"))
                    adapter.submitList(storyList)
                }
    }

    /*TO INCLDUE: search bar, listed stories (for now, represented with a container that can be filled later; make sure it's 'scrollable'
    onClick listener for searchBarButton: get text from the searchBar editText thing and store into searchBarText
         */
    //editText when 'enter' is clicked from the keyboard, default to it being equivalent to pressing the 'search' button
}