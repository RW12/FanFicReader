package com.example.fanficreader.view.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.fanficreader.model.StoryDetailsData

class StoryDetailsDiffCallback : DiffUtil.ItemCallback<StoryDetailsData>() {
    override fun areItemsTheSame(oldItem: StoryDetailsData, newItem: StoryDetailsData): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: StoryDetailsData, newItem: StoryDetailsData): Boolean = oldItem == newItem
}