package com.codenesia.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codenesia.githubuser.data.FollowResponseItem
import com.codenesia.githubuser.database.Favorite
import com.codenesia.githubuser.databinding.ItemFavoriteBinding
import com.codenesia.githubuser.databinding.ItemFollowerBinding
import com.codenesia.githubuser.helper.DiffFavCallback
import com.codenesia.githubuser.ui.FavoriteAdapter

class FollowAdapter(private val listFollower : ArrayList<FollowResponseItem>): RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {

    class ListViewHolder(private val binding: ItemFollowerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(follow: FollowResponseItem) {
            binding.tvFollowerName.text = follow.login
            Glide.with(itemView.context)
                .load(follow.avatarUrl)
                .skipMemoryCache(true)
                .into(binding.imgFollower)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemFollowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowAdapter.ListViewHolder(binding)
    }

    override fun getItemCount() : Int {
        return listFollower.size
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val follow = listFollower[position]
        holder.binding(follow)
    }

}