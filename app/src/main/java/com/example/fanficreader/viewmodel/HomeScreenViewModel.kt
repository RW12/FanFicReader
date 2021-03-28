package com.example.fanficreader.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fanficreader.model.StoryDetailsData
import com.example.fanficreader.view.recyclerview.StoryDetailsAdapter
import com.google.firebase.firestore.FirebaseFirestore

private const val TITLE_AND_AUTHOR_KEY = "titleAndAuthor"
private const val SUMMARY_KEY = "summary"
private const val STORY_KEY = "all"
class HomeScreenViewModel(): ViewModel() {
    var storyList = mutableListOf<StoryDetailsData>()
    var storyListL = MutableLiveData<MutableList<StoryDetailsData>>()
    val db = FirebaseFirestore.getInstance()

    init {
        getStories()
    }
    /**
     * update list of stories being shown in adapter
     */
    private fun getStories() {
        //collect the data of the card, use for collectionPath and documentPath variables to navigate to correct place, then add appropriate details
        db.collection(STORY_KEY).get()
                .addOnSuccessListener {
                    it.documents.forEach{
                        val authorText = it.getString(TITLE_AND_AUTHOR_KEY).orEmpty()
                        val summaryText = it.getString(SUMMARY_KEY).orEmpty()
                        val tagsText = "tags"
                        val charText = "characters"
                        storyList.add(StoryDetailsData(authorText, tagsText, summaryText, charText))
                        //if(storyList.size>0)
                        //    Log.d("Kevin","title/author from viewmodel: " + storyList.get(0).titleAndAuthor)
                        storyListL.value = storyList
                    }
                }.addOnFailureListener{
                    Log.d("Kevin","onFailureListener\nerror message: " + it.toString())
                }
    }
}