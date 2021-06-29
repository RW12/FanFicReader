package com.example.fanficreader.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fanficreader.model.StoryDetailsData
import com.example.fanficreader.view.recyclerview.StoryDetailsAdapter
import com.google.firebase.firestore.FirebaseFirestore

private const val STORY_KEY = "fanfic"
private const val TITLE_AND_AUTHOR_KEY = "titleAndAuthor"
private const val SUMMARY_KEY = "summary"
private const val TAGS_KEY = "tags"
private const val CHAR_KEY = "characters"
private const val CHAPTER = "Chapter"
private const val CONTENT_KEY = "content"
class HomeScreenViewModel(): ViewModel() {
    var storyList = mutableListOf<StoryDetailsData>()
    var storyListL = MutableLiveData<MutableList<StoryDetailsData>>()
    val liveDataTitleAuthor by lazy {
        MutableLiveData<String>()
    }
    val liveDataTags by lazy {
        MutableLiveData<String>()
    }
    val liveDataCharacters by lazy {
        MutableLiveData<String>()
    }
    val liveDataSummary by lazy {
        MutableLiveData<String>()
    }
    val db = FirebaseFirestore.getInstance()
    private var currentChapter = 0
    private var currentStory = ""

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
                        val titleAndAuthorText = it.getString(TITLE_AND_AUTHOR_KEY).orEmpty()
                        val summaryText = it.getString(SUMMARY_KEY).orEmpty()
                        var tagsText = ""
                        it.get(TAGS_KEY).forEach { temp -> String
                            tagsText += temp + ", "
                        }
                        tagsText.substring(0,tagsText.length-2)
                        var charText = "characters"
                        it.get(CHAR_KEY).forEach { temp -> String
                            charText += temp + ", "
                        }
                        charText.substring(0,charText.length-2)
                        val idText = it.id
                        storyList.add(StoryDetailsData(titleAndAuthorText, tagsText, summaryText, charText,idText))
                        //if(storyList.size>0)
                        //    Log.d("Kevin","title/author from viewmodel: " + storyList.get(0).titleAndAuthor)
                        storyListL.value = storyList
                    }
                }.addOnFailureListener{
                    Log.d("Kevin","onFailureListener\nerror message: " + it.toString())
                }
    }

    fun updateStoryTitleCard(storyDataObject: StoryDetailsData) {
        liveDataTitleAuthor.value = storyDataObject.titleAndAuthor
        liveDataTags.value = storyDataObject.tags
        liveDataCharacters.value = storyDataObject.characters
        liveDataSummary.value = storyDataObject.summary
    }
    fun updateCurrentStory(index: Int) {
        currentStory = storyList.get(index).id
    }

    fun nextChapter() {
        currentChapter++
        getChapterContent()
    }
    fun prevChapter() {
        currentChapter--
        getChapterContent()
    }
    private fun getChapterContent() {
        db.collection(STORY_KEY).document(currentStory).collection("$CHAPTER $currentChapter").document("offset 1").get()
                .addOnSuccessListener {
                    liveDataSummary.value = it.getString(CONTENT_KEY)
                }
    }
}