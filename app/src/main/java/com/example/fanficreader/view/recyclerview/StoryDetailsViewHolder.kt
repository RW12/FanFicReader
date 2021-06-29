 package com.example.fanficreader.view.recyclerview

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.fanficreader.*
import com.example.fanficreader.model.StoryDetailsData
import com.example.fanficreader.view.*
import com.example.fanficreader.viewmodel.HomeScreenViewModel
import com.example.fanficreader.viewmodel.StoryViewerViewModel

 class StoryDetailsViewHolder(val itemView: View, private val supportFragmentManager: FragmentManager, val viewModel: HomeScreenViewModel) : RecyclerView.ViewHolder(itemView) {
//https://developer.android.com/guide/topics/ui/layout/recyclerview
    //TODO: adjust these global variables depending on story_card_layout.xml
    val titleAuthorView = itemView.findViewById<TextView>(R.id.storyCardTitleAuthor)
    val tagsView = itemView.findViewById<TextView>(R.id.storyTags)
    val summaryView = itemView.findViewById<TextView>(R.id.storySummary)
    val characterView = itemView.findViewById<TextView>(R.id.storyCharacters)

    init {
        //itemView =
    }

    fun bind(storyDataObject: StoryDetailsData, position: Int) {
        titleAuthorView.text = storyDataObject.titleAndAuthor
        tagsView.text = storyDataObject.tags
        summaryView.text = storyDataObject.summary
        characterView.text = storyDataObject.characters
        itemView.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.activityMainContainer, StoryViewerFragment()).addToBackStack(null).commit()
            viewModel.updateStoryTitleCard(storyDataObject)
            viewModel.updateCurrentStory(position)
        }
    }
}