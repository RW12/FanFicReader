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
private const val OFFSET_KEY = "offset01"

class HomeScreenViewModel : ViewModel() {
    private val storyList = mutableListOf<StoryDetailsData>()
    private val db = FirebaseFirestore.getInstance()

    val liveDataTitleAuthor by lazy { MutableLiveData<String>() }
    val liveDataTags by lazy { MutableLiveData<String>() }
    val liveDataCharacters by lazy { MutableLiveData<String>() }
    val liveDataSummary by lazy { MutableLiveData<String>() }
    val liveDataStoryContent by lazy { MutableLiveData<String>() }
    val liveDataChapter by lazy { MutableLiveData(currentChapter) }
    val liveDataStoryList by lazy { MutableLiveData<MutableList<StoryDetailsData>>() }

    var currentChapter = 0
        private set
    var currentStory: String = ""
        private set

    init {
        getStories()
    }

    /**
     * update list of stories being shown in adapter
     * collect the data of the card, use for collectionPath and documentPath variables to navigate
     * to correct place, then add appropriate details
     */
    private fun getStories() {
        db.collection(STORY_KEY).get()
            .addOnSuccessListener { query ->
                query.documents.forEach { document ->
                    val titleAndAuthorText = document.getString(TITLE_AND_AUTHOR_KEY).orEmpty()
                    val summaryText = document.getString(SUMMARY_KEY).orEmpty()
                    //TODO play around with fonts and text sizes; deal with unchecked cast to ArrayList
                    val tagsText = (document.data?.get(TAGS_KEY) as ArrayList<String>?)
                        ?.toCommaSeparatedString().orEmpty()
                    val charText = (document.data?.get(CHAR_KEY) as ArrayList<String>?)
                        ?.toCommaSeparatedString().orEmpty()
                    val idText = document.id
                    storyList.add(
                        StoryDetailsData(
                            titleAndAuthorText,
                            tagsText,
                            summaryText,
                            charText,
                            idText
                        )
                    )
                    liveDataStoryList.value = storyList
                }
            }.addOnFailureListener {
                Log.e("HomeScreenViewModel", "onFailureListener\nerror message: $it")
            }
    }

    private fun ArrayList<String>.toCommaSeparatedString(): String {
        val builder = StringBuilder()
        this.forEachIndexed { index, s ->
            builder.append(s)
                .apply {
                    if (index < size - 1)
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
        currentStory = storyList[index].id
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
        db.collection(STORY_KEY)
            .document(currentStory)
            .collection("$CHAPTER$currentChapter")
            .document(OFFSET_KEY)
            .get()
            .addOnSuccessListener { document ->
                liveDataStoryContent.value = document.getString(CONTENT_KEY)
            }
    }
}