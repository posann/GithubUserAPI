package com.codenesia.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codenesia.githubuser.data.FollowResponseItem

class FollowAdapter(private val listFollower: ArrayList<FollowResponseItem>): RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvUsername : TextView = itemView.findViewById(R.id.tv_follower_name)
        private val imgAvatar : ImageView = itemView.findViewById(R.id.img_follower)
        fun binding(follow: FollowResponseItem) {
            tvUsername.text = follow.login
            Glide.with(itemView.context)
                .load(follow.avatarUrl)
                .skipMemoryCache(true)
                .into(imgAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return FollowAdapter.ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_follower, parent, false)
        )
    }

    override fun getItemCount(): Int = listFollower.size


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val follow = listFollower[position]
        holder.binding(follow)
    }

}