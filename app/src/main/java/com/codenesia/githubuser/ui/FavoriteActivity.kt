package com.codenesia.githubuser.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codenesia.githubuser.DetailUserViewModel
import com.codenesia.githubuser.R
import com.codenesia.githubuser.UserAdapter
import com.codenesia.githubuser.databinding.ActivityFavoriteBinding
import com.codenesia.githubuser.helper.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private var _binding : ActivityFavoriteBinding? = null
    private val binding get() = _binding

    private lateinit var adapter : FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val layoutManager = LinearLayoutManager(this)
        binding?.rvFavoriteData?.layoutManager = layoutManager

        val viewModelFavorite = obtainViewModel(this@FavoriteActivity)
        viewModelFavorite.getAllFavorites().observe(this) { favList ->
            if (favList != null) {
                val adapter = FavoriteAdapter()
                adapter.setListFavorites(favList)
                binding?.rvFavoriteData?.setHasFixedSize(true)
                binding?.rvFavoriteData?.adapter = adapter
            }
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailUserViewModel::class.java]
    }
}