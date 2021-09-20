package com.example.fanficreader.view.recyclerview

import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fanficreader.*
import com.example.fanficreader.model.StoryDetailsData
import com.example.fanficreader.view.*
import com.example.fanficreader.viewmodel.HomeScreenViewModel

class StoryDetailsViewHolder(itemView: View,
                             private val supportFragmentManager: FragmentManager,
                             private val viewModel: HomeScreenViewModel) :
    RecyclerView.ViewHolder(itemView) {
    private val titleAuthorView = itemView.findViewById<TextView>(R.id.storyCardTitleAuthor)
    private val tagsView = itemView.findViewById<TextView>(R.id.storyTags)
    private val summaryView = itemView.findViewById<TextView>(R.id.storySummary)
    private val characterView = itemView.findViewById<TextView>(R.id.storyCharacters)

    fun bind(storyDataObject: StoryDetailsData, position: Int) {
        titleAuthorView.text = storyDataObject.titleAndAuthor
        tagsView.text = storyDataObject.tags
        summaryView.text = storyDataObject.summary
        characterView.text = storyDataObject.characters
        itemView.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activityMainContainer, StoryViewerFragment())
                .addToBackStack(null).commit()
            viewModel.updateStoryTitleCard(storyDataObject)
            viewModel.updateCurrentStory(position)
        }
    }
}