package com.example.fanficreader.view.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fanficreader.R

class StoryDetailsItemDecorator() : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = view.context.resources.getDimensionPixelSize(R.dimen.story_card_padding)
    }
}