package com.codenesia.githubuser.ui

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codenesia.githubuser.DetailUserActivity
import com.codenesia.githubuser.DetailUserViewModel
import com.codenesia.githubuser.R
import com.codenesia.githubuser.UserAdapter
import com.codenesia.githubuser.database.Favorite
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


        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModelFavorite = obtainViewModel(this@FavoriteActivity)
        viewModelFavorite.getAllFavorites().observe(this) { favList ->
            if (favList != null) {
                binding?.tvFavoriteNotFound?.visibility  = View.GONE
                val adapter = FavoriteAdapter()
                adapter.setListFavorites(favList)
                binding?.rvFavoriteData?.setHasFixedSize(true)
                binding?.rvFavoriteData?.adapter = adapter

                adapter.setOnItemClick { data -> selectedFollower(data) }
            }
        }

    }

    private fun selectedFollower(data: Favorite) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.USERNAME, data.username)
        startActivity(intent)
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailUserViewModel::class.java]
    }
}