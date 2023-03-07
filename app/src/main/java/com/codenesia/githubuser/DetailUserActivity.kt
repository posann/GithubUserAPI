package com.codenesia.githubuser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.codenesia.githubuser.data.DetailUserResponse
import com.codenesia.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var sectionPager: SectionPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        sectionPager = SectionPager(this)
        val viewPager : ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPager
        val tabs : TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()

        supportActionBar?.elevation = 0f
        supportActionBar?.title = resources.getString(R.string.detail_user)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        user = intent.getStringExtra(USERNAME).toString()
        showDetailUser(user)

    }




    private fun showDetailUser(username: String) {
        viewModel.getDetail(username)
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.userDetail.observe(this) { detailUser ->
            setDetailUser(detailUser)
        }

        sectionPager.username = username
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailUser(detailUser: DetailUserResponse) {
        Glide.with(this)
            .load(detailUser.avatarUrl)
            .skipMemoryCache(true)
            .into(binding.imgDetailAvatar)

        binding.tvDetailName.text = detailUser.name
        binding.tvDetailUsername.text = detailUser.login
        binding.tvDetailLocation.text = detailUser.location

        binding.tvDetailFollower.text = "${detailUser.followers.toString()} follower"
        binding.tvDetailFollowing.text = "${detailUser.following.toString()} following"
        binding.tvDetailRepositories.text = "${detailUser.publicRepos.toString()} repositories"
        val company = detailUser.company ?: "Compani Tidak Tersedia"
        val bios = detailUser.bio ?: "Bio Tidak Tersedia"
        binding.tvDetailCompany.text = company
        binding.tvDetailBio.text = bios as CharSequence?
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) =
        binding.progressBar.visibility == if (isLoading) View.VISIBLE else View.GONE


    companion object {
        const val USERNAME = "username"
        var user = String()

        private val TAB_TITLE = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }
}