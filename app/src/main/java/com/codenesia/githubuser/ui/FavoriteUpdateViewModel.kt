package com.codenesia.githubuser.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import com.codenesia.githubuser.database.Favorite
import com.codenesia.githubuser.repository.FavoriteRepository

class FavoriteUpdateViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }
}