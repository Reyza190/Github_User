package com.example.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.data.api.ItemsItem
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.viewmodel.DetailUserViewModel
import com.example.githubuser.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel by viewModels<DetailUserViewModel> {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getFavoriteUser().observe(this){user ->
            val items = arrayListOf<ItemsItem>()
            user.map {
                val item = ItemsItem(login = it.userName, avatarUrl = it.avatarUrl!!)
                items.add(item)
            }
            binding.rvFavUser.adapter = UserAdapter(items)
        }
        setRecyleView()

    }
    private fun setRecyleView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavUser.addItemDecoration(itemDecoration)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}