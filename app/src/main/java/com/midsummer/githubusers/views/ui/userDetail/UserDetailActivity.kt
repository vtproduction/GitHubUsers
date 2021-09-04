package com.midsummer.githubusers.views.ui.userDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.midsummer.githubusers.R
import com.midsummer.githubusers.databinding.ActivityUserDetailBinding
import com.midsummer.githubusers.di.AppModule
import com.midsummer.githubusers.di.DaggerAppComponent
import com.midsummer.githubusers.internal.randomImageUrl
import com.midsummer.githubusers.viewModel.UserDetailViewModel
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {

    private val viewModel: UserDetailViewModel by viewModels()

    private val userName : String? by lazy {
        intent.getStringExtra("userName")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerAppComponent.builder()
            .appModule(AppModule())
            .build().inject(this)

        observeLiveData()

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        userName?.let {
            viewModel.fetchData(it)
        }

        swipeRefreshLayout.setOnRefreshListener {
            userName?.let {
                viewModel.fetchData(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun observeLiveData(){
        observeInProgress()
        observeIsError()
        observeUserDetail()
    }

    private fun observeInProgress() {
        viewModel.repository.isInProgress.observe(this,  { isLoading ->
            isLoading.let {
                if (it) {
                    notFoundLayout.visibility = View.GONE
                    contentContainer.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = true
                } else {
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    private fun observeIsError() {
        viewModel.repository.isError.observe(this,  { isError ->
            isError.let {
                if (it) {
                    disableViewsOnError()
                } else {
                    notFoundLayout.visibility = View.GONE
                    contentContainer.visibility = View.VISIBLE
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    private fun disableViewsOnError() {
        swipeRefreshLayout.isRefreshing = false
        notFoundLayout.visibility = View.VISIBLE
        contentContainer.visibility = View.GONE
    }

    private fun observeUserDetail(){
        viewModel.repository.data.observe(this, {userDetail ->
            userDetail.let {
                val request = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(imgAva)
                    .load(it.avatarUrl)
                    .circleCrop()
                    .apply(request)
                    .placeholder(R.drawable.placeholder_ava)
                    .fallback(R.drawable.placeholder_ava)
                    .into(imgAva)
                Glide.with(imgCover)
                    .load(randomImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.img_place_holder_cover)
                    .fallback(R.drawable.img_place_holder_cover)
                    .skipMemoryCache(true)
                    .into(imgCover)

                txtUserName.text = it.username
                txtUserLocation.text = if (it.location.isNullOrBlank()) "No location found" else it.location
                txtUserBio.text = if (it.bio.isNullOrBlank()) "No bio found." else it.bio
                txtFollowers.text = it.followers.toString()
                txtFollowing.text = it.following.toString()
                txtRepos.text = it.publicRepo.toString()
            }
        })
    }

    companion object {
        fun open(from: AppCompatActivity, userName: String){
            val intent = Intent(from, UserDetailActivity::class.java)
            intent.putExtra("userName", userName)
            from.startActivity(intent)
        }
    }

}