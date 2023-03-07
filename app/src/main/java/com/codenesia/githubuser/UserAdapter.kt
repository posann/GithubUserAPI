package com.codenesia.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class UserAdapter(private val listUser: ArrayList<ItemsUser>): RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvUsername : TextView = itemView.findViewById(R.id.tv_username)
        private val tvUrl : TextView = itemView.findViewById(R.id.tv_url)
        private val imgAvatar : ImageView = itemView.findViewById(R.id.img_avatar)
        fun binding(user: ItemsUser) {
            tvUsername.text = user.login
            tvUrl.text = user.url
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .skipMemoryCache(true)
                .into(imgAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return  ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.binding(user)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(
                listUser[position]
            )
        }
    }

    fun setOnItemClick(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun interface OnItemClickCallback {
        fun onItemClicked(data: ItemsUser)
    }
}