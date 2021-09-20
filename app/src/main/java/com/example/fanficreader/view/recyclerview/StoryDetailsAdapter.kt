package com.example.fanficreader.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import com.example.fanficreader.R
import com.example.fanficreader.model.StoryDetailsData
import com.example.fanficreader.viewmodel.HomeScreenViewModel

class StoryDetailsAdapter(private val supportFragmentManager: FragmentManager,
                          val viewModel: HomeScreenViewModel) :
    ListAdapter<StoryDetailsData, StoryDetailsViewHolder>(StoryDetailsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryDetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.story_card_layout, parent, false)
        return StoryDetailsViewHolder(view, supportFragmentManager, viewModel)
    }

    override fun onBindViewHolder(holder: StoryDetailsViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}