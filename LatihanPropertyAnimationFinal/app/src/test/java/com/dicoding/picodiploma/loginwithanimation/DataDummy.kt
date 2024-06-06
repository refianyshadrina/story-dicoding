package com.dicoding.picodiploma.loginwithanimation

import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem

object DataDummy {

    fun generateDummyQuoteResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {

            val story = ListStoryItem(
                null,
                null,
                "Userdummy",
                "Descriptiondummy",
                null,
                "Story $i",
                null,
            )
            items.add(story)
        }
        return items
    }
}