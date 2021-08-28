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
import com.example.fanficreader.viewmodel.CHAPTER
import com.example.fanficreader.viewmodel.HomeScreenViewModel
import com.google.firebase.firestore.FirebaseFirestore

private const val STORY_KEY = "fanfic"
private const val CHAPTER = "Chapter"
class StoryViewerFragment : Fragment() {
    val viewModel: HomeScreenViewModel by activityViewModels()

    //put the chapter nav spinner here? Also need to create the SpinnerAdapter here too if so.
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

        //onClickListener for title screen next button
        view.findViewById<Button>(R.id.titleScreenNextButton).setOnClickListener {
            viewModel.nextChapter() //for now, this is hardcoded to only get the first offset after one button press
            //set other textview's visibility to 'gone'
            titleAuthorTextView.visibility = View.GONE
            tagsTextView.visibility = View.GONE
            charTextView.visibility = View.GONE
            summaryTextView.visibility = View.GONE
            storyContentTextView.visibility = View.VISIBLE
            prevButton.visibility = View.VISIBLE
        }
        //onClickListener for prev button
        prevButton.setOnClickListener {
            viewModel.prevChapter()
        }
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

        //populating chapter_nav_spinner with stuff
        val chapterNavSpinner: Spinner = view.findViewById(R.id.chapter_nav_spinner)
        //Create an ArrayAdapter using the string array and a default spinner layout
        val spinnerAdapter = ArrayAdapter<String>(chapterNavSpinner.context, android.R.layout.simple_spinner_item, android.R.id.text1)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        chapterNavSpinner.adapter = spinnerAdapter
        //TODO get array of collections from the document (document = story, collection = chapter); also add title card thing?
        val db = FirebaseFirestore.getInstance()
        /*val listOfChapters = db.collection(STORY_KEY).document(viewModel.getCurrentStory())
        spinnerAdapter.addAll(listOfChapters)*/
        val testList = arrayListOf("Chapter 1", "Chapter 2", "Chapter 3")
        spinnerAdapter.addAll(testList)
        spinnerAdapter.notifyDataSetChanged()
        //click handler for spinner
        chapterNavSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //TODO: include the title card into the spinner and allow user to navigate to it? Seems hard so ignoring it for now
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                db.collection(STORY_KEY).document(viewModel.getCurrentStory()).collection("$CHAPTER$${viewModel.getCurrentChapter()}$").get()
                        .addOnSuccessListener {
                            //TODO doing this correctly?
                            viewModel.updateChapter(pos)
                        }
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
                //
            }
        }
    }
    //super.onViewCreated(view, savedInstanceState)
}