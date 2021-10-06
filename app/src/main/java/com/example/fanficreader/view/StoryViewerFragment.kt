package com.example.fanficreader.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.fanficreader.R
import com.example.fanficreader.viewmodel.HomeScreenViewModel
import com.google.firebase.firestore.FirebaseFirestore

private const val STORY_KEY = "fanfic"
private const val CHAPTER = "Chapter"
private const val CHAPTERS_KEY = "chapters"

class StoryViewerFragment : Fragment() {
    val viewModel: HomeScreenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_story_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val titleAuthorTextView = view.findViewById<TextView>(R.id.titleAndAuthor)
        val summaryTextView = view.findViewById<TextView>(R.id.summary)
        val tagsTextView = view.findViewById<TextView>(R.id.tags)
        val charTextView = view.findViewById<TextView>(R.id.characters)
        val storyContentTextView = view.findViewById<TextView>(R.id.storyContent)
        val prevButton = view.findViewById<Button>(R.id.titleScreenPrevButton)

        viewModel.liveDataTitleAuthor.observe(viewLifecycleOwner, Observer<String> {
            titleAuthorTextView.text = it
        })
        viewModel.liveDataSummary.observe(viewLifecycleOwner, Observer<String> {
            summaryTextView.text = it
        })
        viewModel.liveDataTags.observe(viewLifecycleOwner, Observer<String> {
            tagsTextView.text = it
        })
        viewModel.liveDataCharacters.observe(viewLifecycleOwner, Observer<String> {
            charTextView.text = it
        })
        viewModel.liveDataStoryContent.observe(viewLifecycleOwner, Observer<String> {
            storyContentTextView.text = it
        })

        //onClickListener for the 'next' button
        view.findViewById<Button>(R.id.titleScreenNextButton).setOnClickListener {
            viewModel.nextChapter()
            titleAuthorTextView.visibility = View.GONE
            tagsTextView.visibility = View.GONE
            charTextView.visibility = View.GONE
            summaryTextView.visibility = View.GONE
            storyContentTextView.visibility = View.VISIBLE
            prevButton.visibility = View.VISIBLE
        }
        //onClickListener for thje 'prev' button
        prevButton.setOnClickListener {
            viewModel.prevChapter()
        }
        //setting proper elements in place if the chapter goes back to the 0th chapter, or the title screen
        viewModel.liveDataChapter.observe(viewLifecycleOwner, Observer<Int> { chapter ->
            if (chapter == 0) {
                prevButton.visibility = View.GONE
                titleAuthorTextView.visibility = View.VISIBLE
                tagsTextView.visibility = View.VISIBLE
                charTextView.visibility = View.VISIBLE
                summaryTextView.visibility = View.VISIBLE
                storyContentTextView.visibility = View.INVISIBLE
            }
        })

        /*
        TODO get array of collections from the document (document = story, collection = chapter) as
            well as ability to go to title card (0th chapter)
        TODO [list of chapters in spinner dynamically changes]: have to use onsuccesslistener
        TODO add chapter number in front of chapter title in the chapter nav spinner
         */
        val chapterNavSpinner: Spinner = view.findViewById(R.id.chapter_nav_spinner)
        val spinnerAdapter = ArrayAdapter<String>(chapterNavSpinner.context,
            android.R.layout.simple_spinner_item,
            android.R.id.text1)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        chapterNavSpinner.adapter = spinnerAdapter
        val db = FirebaseFirestore.getInstance()
        db.collection(STORY_KEY).document(viewModel.currentStory).get().addOnCompleteListener { task->
            val listOfChapters: List<String> = task.getResult()?.get(CHAPTERS_KEY) as List<String>
            spinnerAdapter.addAll(listOfChapters)
        }
        spinnerAdapter.notifyDataSetChanged()
        //click handler for spinner
        chapterNavSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                db.collection(STORY_KEY)
                    .document(viewModel.currentStory)
                    .collection("$CHAPTER${viewModel.currentChapter}")
                    .get()
                    .addOnSuccessListener {
                        viewModel.updateChapter(pos)
                    }
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
                //empty for now
            }
        }
    }
}