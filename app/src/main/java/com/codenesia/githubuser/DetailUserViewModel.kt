package com.codenesia.githubuser

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codenesia.githubuser.database.Favorite
import com.codenesia.githubuser.repository.FavoriteRepository

class DetailUserViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites() : LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()

}