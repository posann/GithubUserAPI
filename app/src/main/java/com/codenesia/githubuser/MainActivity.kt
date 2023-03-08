package com.codenesia.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codenesia.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        showAdapterData()
        showViewModel()

    }

    private fun showViewModel() {
        viewModel.listUser.observe(this) { user -> setDataUser(user) }
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.searchUser("Ahmad Fauzan")
    }

    private fun showAdapterData() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUserData.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserData.addItemDecoration(itemDecoration)

    }


    private fun showLoading(isLoading: Boolean?) {
        binding.progressBar.visibility = if (isLoading == true) View.VISIBLE else View.GONE
    }

    private fun setDataUser(user: ArrayList<ItemsUser>) {
        val adapter = UserAdapter(user)
        binding.rvUserData.setHasFixedSize(true)
        binding.rvUserData.adapter = adapter
        adapter.setOnItemClick { data -> selectedUser(data) }
    }

    private fun selectedUser(user: ItemsUser) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.USERNAME, user.login)
        startActivity(intent)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchUser(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })


        return true
    }

}