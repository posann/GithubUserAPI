package com.codenesia.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codenesia.githubuser.api.ApiConfig
import com.codenesia.githubuser.data.DetailUserResponse
import com.codenesia.githubuser.data.FollowResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _listUser = MutableLiveData<ArrayList<ItemsUser>>()
    val listUser: LiveData<ArrayList<ItemsUser>> = _listUser

    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val userDetail: LiveData<DetailUserResponse> = _userDetail

    private val _listFollower = MutableLiveData<ArrayList<FollowResponseItem>>()
    val listFollower : LiveData<ArrayList<FollowResponseItem>> = _listFollower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object {
        private const val TAG = "MainViewModel"
    }

    fun searchUser(username: String ) {
        try {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getUser(username)
            client.enqueue(object : Callback<GithubResponse> {
                override fun onResponse(
                    call: Call<GithubResponse>,
                    response: Response<GithubResponse>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _listUser.value = ArrayList(responseBody.items)
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
    }

    fun getDetail(username: String) {
        try {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getDetailUser(username)
            client.enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _userDetail.value = response.body()
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }

    fun getDetailFollower(username: String) {
        try {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getFollower(username)
            client.enqueue(object : Callback<ArrayList<FollowResponseItem>> {
                override fun onResponse(
                    call: Call<ArrayList<FollowResponseItem>>,
                    response: Response<ArrayList<FollowResponseItem>>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful) {
                        if (responseBody != null) {
                            _listFollower.value = responseBody!!
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<FollowResponseItem>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })

        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
    }

    fun getDetailFollowing(username: String) {
        try {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getFollowing(username)
            client.enqueue(object : Callback<ArrayList<FollowResponseItem>> {
                override fun onResponse(
                    call: Call<ArrayList<FollowResponseItem>>,
                    response: Response<ArrayList<FollowResponseItem>>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful) {
                        if (responseBody != null) {
                            _listFollower.value = responseBody!!
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<FollowResponseItem>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })

        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
    }
}