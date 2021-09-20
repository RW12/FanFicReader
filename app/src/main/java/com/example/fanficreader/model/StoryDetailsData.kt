package com.example.fanficreader.model

//TODO change later as necessary; also change story_card_layout accordingly
data class StoryDetailsData(val titleAndAuthor: String = "Title by Author",
                            val tags: String = "Tags or keywords that describe the type of story.",
                            val summary: String = "Summary of the story.",
                            val characters: String = "List of characters featured in the story.",
                            val id: String = "id of story from firebase")