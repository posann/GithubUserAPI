package com.codenesia.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codenesia.githubuser.database.Favorite
import com.codenesia.githubuser.databinding.ItemFavoriteBinding
import com.codenesia.githubuser.helper.DiffFavCallback

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val listFavorites = ArrayList<Favorite>()
    private lateinit var favoriteUpdateViewModel: FavoriteUpdateViewModel


    fun setListFavorites(listFavorites : List<Favorite>) {
        val diffCallback = DiffFavCallback(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)

        diffResult.dispatchUpdatesTo(this)
    }

    class FavoriteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fav : Favorite) {
            binding.tvFavoriteUsername.text = fav.username
            Glide.with(itemView.context)
                .load(fav.urlImage)
                .skipMemoryCache(true)
                .into(binding.imgFavoriteAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    fun setOnClickCallback(favList: List<Favorite>) {

    }
}