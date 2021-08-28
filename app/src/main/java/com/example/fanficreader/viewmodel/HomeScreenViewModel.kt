package com.example.fanficreader.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fanficreader.model.StoryDetailsData
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
    val liveDataStoryContent by lazy {
        MutableLiveData<String>()
    }
    val liveDataChapter by lazy {
        MutableLiveData<Int>(currentChapter)
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
                        //TODO PLAY AROUND WITH FONTS AND SIZES AND STUFF and the below unchecked cast to ArrayList
                        val tagsText =  (it.data?.get(TAGS_KEY) as ArrayList<String>?)?.toCommaSeparatedString().orEmpty()
                        val charText = (it.data?.get(CHAR_KEY) as ArrayList<String>?)?.toCommaSeparatedString().orEmpty()
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

    fun ArrayList<String>.toCommaSeparatedString(): String {
        val builder = StringBuilder();
        this.forEachIndexed { index, s ->
            builder.append(s)
                    .apply {
                        if(index < size-1)
                            append(", ")
                    }
        }
        return builder.toString()
    }

    fun updateStoryTitleCard(storyDataObject: StoryDetailsData) {
        liveDataTitleAuthor.value = storyDataObject.titleAndAuthor
        liveDataTags.value = storyDataObject.tags
        liveDataCharacters.value = storyDataObject.characters
        liveDataSummary.value = storyDataObject.summary
    }
    fun updateCurrentStory(index: Int) {
        currentStory = storyList.get(index).id
        println("Kevin: 1 $currentStory")
    }

    fun nextChapter() {
        currentChapter++
        liveDataChapter.value = currentChapter
        getChapterContent()
    }
    fun prevChapter() {
        currentChapter--
        liveDataChapter.value = currentChapter
        getChapterContent()
    }
    fun updateChapter(chapter: Int) {
        currentChapter = chapter
        liveDataChapter.value = currentChapter
        getChapterContent()
    }
    private fun getChapterContent() {
        db.collection(STORY_KEY).document(currentStory).collection("$CHAPTER$currentChapter").document("offset01").get()
                .addOnSuccessListener {
                    println("Kevin: 2 ${it.getString(CONTENT_KEY)}")
                    liveDataStoryContent.value = it.getString(CONTENT_KEY)
                }
    }

    fun getCurrentStory(): String  {
        return currentStory;
    }
    fun getCurrentChapter(): Int  {
        return currentChapter;
    }
}