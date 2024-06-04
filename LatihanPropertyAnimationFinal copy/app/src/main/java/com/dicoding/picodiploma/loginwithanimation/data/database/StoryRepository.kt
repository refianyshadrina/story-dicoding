package com.dicoding.picodiploma.loginwithanimation.data.database

import android.app.Application
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StoryRepository (private val apiService: ApiService, private val userPreference: UserPreference, private val dao: StoryDao) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun insert(story: List<Story>) {
        executorService.execute { dao.insert(story) }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService, userPreference: UserPreference, dao: StoryDao
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, userPreference, dao)
            }.also { instance = it }
    }
}