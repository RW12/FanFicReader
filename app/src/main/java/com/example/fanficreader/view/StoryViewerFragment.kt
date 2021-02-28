package com.example.fanficreader.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fanficreader.R

const val SUMMARY_BUNDLE_KEY = "Summary"
class StoryViewerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_story_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val summaryString = arguments?.getString(SUMMARY_BUNDLE_KEY)
        view.findViewById<TextView>(R.id.summary).text = summaryString.orEmpty()

    //super.onViewCreated(view, savedInstanceState)
    }
}