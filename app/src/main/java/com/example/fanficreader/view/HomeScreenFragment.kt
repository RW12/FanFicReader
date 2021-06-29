package com.example.fanficreader.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fanficreader.R
import com.example.fanficreader.view.recyclerview.StoryDetailsAdapter
import com.example.fanficreader.model.StoryDetailsData
import com.example.fanficreader.view.recyclerview.StoryDetailsItemDecorator
import com.example.fanficreader.viewmodel.HomeScreenViewModel

class HomeScreenFragment(private val supportFragmentManager: FragmentManager) : Fragment(R.layout.fragment_home_screen) {
    val viewModel: HomeScreenViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
    }

    /**
     * setting up recycler view and adapter
     */
    fun initRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.listedStoryRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = StoryDetailsAdapter(supportFragmentManager, viewModel)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(StoryDetailsItemDecorator())

        viewModel.storyListL.observe(viewLifecycleOwner, Observer<MutableList<StoryDetailsData>> {
            //if(it.size>0)
            //    Log.d("Kevin","title/author from fragment: " + it.get(0).titleAndAuthor)
            adapter.submitList(it)
        })

    }

    /*TO INCLUDE: search bar, listed stories (for now, represented with a container that can be filled later; make sure it's 'scrollable'
    onClick listener for searchBarButton: get text from the searchBar editText thing and store into searchBarText
         */
    //editText when 'enter' is clicked from the keyboard, default to it being equivalent to pressing the 'search' button
}