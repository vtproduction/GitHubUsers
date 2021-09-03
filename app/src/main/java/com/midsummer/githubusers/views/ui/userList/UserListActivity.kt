package com.midsummer.githubusers.views.ui.userList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.midsummer.githubusers.R
import com.midsummer.githubusers.di.AppModule
import com.midsummer.githubusers.di.DaggerAppComponent
import com.midsummer.githubusers.model.GithubUser
import com.midsummer.githubusers.repository.UserListRepository
import com.midsummer.githubusers.viewModel.UserListViewModel
import com.midsummer.githubusers.views.adapter.UserClickListener
import com.midsummer.githubusers.views.adapter.UserListAdapter
import com.midsummer.githubusers.views.ui.userDetail.UserDetailActivity
import kotlinx.android.synthetic.main.activity_user_list.*
import javax.inject.Inject

class UserListActivity : AppCompatActivity(), UserClickListener {

    @Inject
    lateinit var userListAdapter: UserListAdapter

    private val viewModel: UserListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_user_list)



        DaggerAppComponent.builder()
            .appModule(AppModule())
            .build().inject(this)

        setUpRecyclerView()
        userListAdapter.setCallback(this)
        observeLiveData()

        setSupportActionBar(toolbar2)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        swipeRefreshLayout2.setOnRefreshListener {
            swipeRefreshLayout2.isRefreshing = false
            viewModel.repository.fetchDataFromDatabase()
        }
    }

    private fun setUpRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = userListAdapter
        }
    }

    private fun observeLiveData() {
        observeInProgress()
        observeIsError()
        observeUserList()
    }

    private fun observeInProgress() {
        viewModel.repository.isInProgress.observe(this,  { isLoading ->
            isLoading.let {
                if (it) {
                    txtEmpty.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    loadingProgress.visibility = View.VISIBLE
                } else {
                    loadingProgress.visibility = View.GONE
                }
            }
        })
    }

    private fun observeIsError() {
        viewModel.repository.isError.observe(this, Observer { isError ->
            isError.let {
                if (it) {
                    disableViewsOnError()
                } else {
                    txtEmpty.visibility = View.GONE
                    loadingProgress.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun disableViewsOnError() {
        loadingProgress.visibility = View.VISIBLE
        txtEmpty.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        userListAdapter.setUpData(emptyList())
        loadingProgress.visibility = View.GONE
    }

    private fun observeUserList() {
        viewModel.repository.data.observe(this,  { data ->
            data.let {
                if (it != null && it.isNotEmpty()) {
                    loadingProgress.visibility = View.VISIBLE
                    recyclerView.visibility = View.VISIBLE
                    userListAdapter.setUpData(it)
                    txtEmpty.visibility = View.GONE
                    loadingProgress.visibility = View.GONE
                } else {
                    disableViewsOnError()
                }
            }
        })
    }

    override fun onUserClicked(user: GithubUser) {
        UserDetailActivity.open(this, user.username)
    }
}