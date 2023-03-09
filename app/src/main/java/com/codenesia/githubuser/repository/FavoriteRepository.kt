package com.codenesia.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.codenesia.githubuser.database.Favorite
import com.codenesia.githubuser.database.FavoriteDao
import com.codenesia.githubuser.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {

    private val mFavDao : FavoriteDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavDao = db.favoriteDao()
    }

    fun getAllFavorites() : LiveData<List<Favorite>> = mFavDao.getAllFavorites()

    fun insert(favorite: Favorite){
        executorService.execute { mFavDao.insert(favorite) }
    }

    fun delete(favorite: Favorite){
        executorService.execute { mFavDao.delete(favorite) }
    }

}