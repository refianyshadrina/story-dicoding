package com.dicoding.picodiploma.loginwithanimation.data.database

import androidx.room.*

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(story: List<Story>)

    @Query("SELECT * FROM Story")
    fun getAllStory(): List<Story>

}