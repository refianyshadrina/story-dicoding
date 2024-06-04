package com.dicoding.picodiploma.loginwithanimation.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.Response

class SignupViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun signup(name: String, email: String, password: String, token: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService("Bearer $token").register(name, email, password)
        api.enqueue(object : retrofit2.Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _message.value = "Akun berhasil dibuat"
                } else {
                    when (response.code()) {
                        400 -> _message.value =
                            "Email sudah pernah digunakan"
                        else -> {
                            _message.value = response.message()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message.toString()
            }

        })
    }
}