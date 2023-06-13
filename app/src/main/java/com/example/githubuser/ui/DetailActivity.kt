package com.example.githubuser.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.data.api.DetailUserResponse
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.viewmodel.DetailUserViewModel
import com.example.githubuser.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {


    private val detailViewModel by viewModels<DetailUserViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityDetailBinding
    private var isFavorite: Boolean = false
    private var userName: String? = ""
    private var avatarUrl: String? = ""

    companion object {
        private val TAB_TITLE = intArrayOf(
            R.string.tab1,
            R.string.tab2
        )
        private const val KEY = "KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra(KEY)
        if (userName != null) {
            detailViewModel.getUser(userName)
        }

        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionPagerAdapter = SectionsPagerAdapter(this)
        if (userName != null) {
            sectionPagerAdapter.username = userName
        }

        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])

        }.attach()

        supportActionBar?.elevation = 0f

        detailViewModel.user.observe(this){user ->
            setDataUser(user)
        }

        detailViewModel.message.observe(this){message ->
            toastMessage(message)
        }

        detailViewModel.isLoading.observe(this) { loading ->
            showLoading(loading)
        }

        if (userName != null) {
            detailViewModel.getFavoriteByUsername(userName).observe(this) { user ->
                if (user?.userName != null) {
                    isFavorite = true
                    binding.fabFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.fabFavorite.context,
                            R.drawable.baseline_favorite
                        )
                    )
                }
            }
        }

        binding.fabFavorite.setOnClickListener{
            if (!isFavorite) {
                isFavorite = true
                val user = FavoriteUser(userName!!, avatarUrl, false)
                detailViewModel.setFavoriteUser(user)
                toastMessage("$userName berhasil ditambahkan")
            }else{
                isFavorite = false
                detailViewModel.deleteFavoriteUser(userName!!)
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavorite.context,
                        R.drawable.baseline_favorite_border
                    )
                )
                toastMessage("$userName berhasil dihapus")
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setDataUser(user: DetailUserResponse) {
        Glide.with(this).load(user.avatarUrl).into(binding.civUser)
        avatarUrl = user.avatarUrl
        binding.tvUsername.text = user.login
        userName = user.login
        binding.tvFullName.text = user.name
        binding.tvJumlahFollowers.text =
            user.followers?.toString().toString() + " " + getString(R.string.follwers)
        binding.tvJumlahFollowing.text =
            user.following?.toString() + " " + getString(R.string.following)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun toastMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}