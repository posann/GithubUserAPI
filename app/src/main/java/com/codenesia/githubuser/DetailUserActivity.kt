package com.codenesia.githubuser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.codenesia.githubuser.data.DetailUserResponse
import com.codenesia.githubuser.database.Favorite
import com.codenesia.githubuser.databinding.ActivityDetailUserBinding
import com.codenesia.githubuser.helper.FavoriteViewModelFactory
import com.codenesia.githubuser.ui.FavoriteUpdateViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var sectionPager: SectionPager

    private lateinit var detailViewModel: DetailUserViewModel

    private var favorite: Favorite = Favorite()
    private lateinit var favoriteUpdateViewModel: FavoriteUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteUpdateViewModel = obtainViewModel(this@DetailUserActivity)


        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        sectionPager = SectionPager(this)


        binding.detailProgressBar.visibility = View.VISIBLE

        val viewPager : ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPager
        val tabs : TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()

        user = intent.getStringExtra(USERNAME).toString()

        supportActionBar?.elevation = 0f
        supportActionBar?.title = user
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showDetailUser(user)

        viewModel.userDetail.observe(this) { detailUser ->
            setDetailUser(detailUser)
            addFavorites(detailUser)
        }
    }

    private fun addFavorites(user: DetailUserResponse) {
        val userfav = user.login.toString().trim()
        val urlImage = user.avatarUrl.toString().trim()
        favoriteUpdateViewModel.getFavoriteUserByUsername(userfav).observe(this) { fav ->
            if (fav != null && fav.username == userfav) {
                binding.btnDetailAdd.text = getString(R.string.buttonIsFavorite)
                binding.btnDetailAdd.setOnClickListener {
                    favorite.let { favorite ->
                        favorite.username = userfav
                        favorite.urlImage = urlImage
                    }
                    favoriteUpdateViewModel.delete(favorite)
                    showToast("Data " + favorite.username + " telah dihapus")
                    finish()
                }
            } else {
                binding.btnDetailAdd.text = getString(R.string.buttonFavorite)
                binding.btnDetailAdd.setOnClickListener {
                    favorite.let { favorite ->
                        favorite.username = userfav
                        favorite.urlImage = urlImage
                    }
                    favoriteUpdateViewModel.insert(favorite)
                    showToast("Data telah ditambahkan")
                    finish()
                }
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun showDetailUser(username: String) {
        viewModel.getDetail(username)
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
        binding.tvDetailLocation.text = detailUser.location ?: "[Lokasi Tidak Tersedia]"

        binding.tvDetailFollower.text = "${detailUser.followers.toString()} Follower"
        binding.tvDetailFollowing.text = "${detailUser.following.toString()} Following"
        binding.tvDetailRepositories.text = "${detailUser.publicRepos.toString()} Repositories"
        binding.tvDetailBio.text = (detailUser.bio ?: "[Bio Tidak Tersedia]") as CharSequence?
        binding.detailProgressBar.visibility = View.GONE

    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUpdateViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUpdateViewModel::class.java]
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) =
        binding.detailProgressBar.visibility == if (isLoading) View.VISIBLE else View.GONE


    companion object {
        const val USERNAME = "username"
        var user = String()

        private val TAB_TITLE = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}