package com.dicoding.picodiploma.loginwithanimation.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.database.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _detailStory = MutableLiveData<ListStoryItem>()
    val detailStory: LiveData<ListStoryItem> = _detailStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }


    fun getStoryById(id: String, token: String) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService(token)
        apiService.getStoryById(id).enqueue(object : Callback<ListStoryItem> {
            override fun onResponse(
                call: Call<ListStoryItem>,
                response: Response<ListStoryItem>
            ) {
                _isLoading.value = false

                if (response.isSuccessful && response.body() != null) {
                    _detailStory.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListStoryItem>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "DetailUserViewModel"
    }
}

