package com.example.fanficreader.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import com.example.fanficreader.R
import com.example.fanficreader.model.StoryDetailsData

class StoryDetailsAdapter(private val supportFragmentManager: FragmentManager) : ListAdapter<StoryDetailsData, StoryDetailsViewHolder>(StoryDetailsDiffCallback()) {
    //https://medium.com/simform-engineering/listadapter-a-recyclerview-adapter-extension-5359d13bd879
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryDetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.story_card_layout, parent, false)
        return StoryDetailsViewHolder(view, supportFragmentManager)
    }

    override fun onBindViewHolder(holder: StoryDetailsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}