package com.example.githubuser.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.GithubRepository
import com.example.githubuser.data.api.ApiConfig
import com.example.githubuser.data.api.DetailUserResponse
import com.example.githubuser.data.api.ItemsItem
import com.example.githubuser.data.local.entity.FavoriteUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(private val githubRepository: GithubRepository) : ViewModel() {

    private val _user = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val client = ApiConfig.getApiService()

    companion object {
        private const val TAG = "DetailActivity"
    }

    fun getUser(username: String) {
        _isLoading.value = true
        client.getDetailUser(username).enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _message.value = t.message
            }

        })
    }

    fun getFollowersUser(username: String) {
        _isLoading.value = true
        client.getFollowers(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _message.value = t.message
            }

        })
    }

    fun getFollowingUser(username: String) {
        _isLoading.value = true
        client.getFollowing(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _message.value = t.message
            }

        })
    }

    fun setFavoriteUser(favoriteUser: FavoriteUser) {
        githubRepository.setFavoriteUser(favoriteUser, true)
    }

    fun deleteFavoriteUser(username: String) {
        githubRepository.deleteFavoriteUser(username)
    }

    fun getFavoriteUser() = githubRepository.getFavoriteUser()

    fun getFavoriteByUsername(username: String) =
        githubRepository.getFavoriteUserbyUsername(username)
}