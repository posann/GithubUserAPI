package com.codenesia.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.codenesia.githubuser.database.Favorite

class DiffFavCallback(private val mOldFav: List<Favorite>, private val mNewFav: List<Favorite>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFav.size
    }

    override fun getNewListSize(): Int {
        return mNewFav.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFav[oldItemPosition].username == mNewFav[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFav[oldItemPosition]
        val newEmployee = mNewFav[newItemPosition]

        return oldEmployee.username == newEmployee.username && oldEmployee.urlImage == newEmployee.urlImage
    }
}