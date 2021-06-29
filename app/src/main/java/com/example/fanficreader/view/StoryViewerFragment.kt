package com.example.fanficreader.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.fanficreader.R
import com.example.fanficreader.model.StoryDetailsData
import com.example.fanficreader.viewmodel.HomeScreenViewModel
import com.example.fanficreader.viewmodel.StoryViewerViewModel

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

        //onClickListener for title screen next button
        view.findViewById<Button>(R.id.titleScreenNextButton).setOnClickListener {
            viewModel.nextChapter() //for now, this is hardcoded to only get the first offset after one button press
            //TODO find some way to replace the fragment on screen with the story chapter view fragment (from xml file)?
            //set other textview's visibility to 'gone'
            titleAuthorTextView.visibility = View.GONE
            tagsTextView.visibility = View.GONE
            charTextView.visibility = View.GONE
            //prev button visibility true
        }
        //connected to StoryDetailsViewHolder, ~line 35
    }
    //super.onViewCreated(view, savedInstanceState)
}