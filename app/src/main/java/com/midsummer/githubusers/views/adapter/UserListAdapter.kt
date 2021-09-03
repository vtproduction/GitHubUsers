package com.midsummer.githubusers.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.midsummer.githubusers.R
import com.midsummer.githubusers.databinding.UserListItemBinding
import com.midsummer.githubusers.model.GithubUser
import javax.inject.Singleton

/**
 * Created by nienle on 03,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class UserListAdapter(private val data: ArrayList<GithubUser>) : RecyclerView.Adapter<UserItemViewHolder>()  {

    private var callback : UserClickListener? = null

    fun setCallback(callback: UserClickListener) {
        this.callback = callback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val itemViewBinding: UserListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.user_list_item,
            parent,
            false
        )
        return UserItemViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.viewBinding.model = data[position]
        holder.viewBinding.callback = callback
    }

    override fun getItemCount(): Int = data.size

    fun setUpData(articles: List<GithubUser>) {
        data.clear()
        data.addAll(articles)
        notifyDataSetChanged()
    }

}

class UserItemViewHolder(
    val viewBinding: UserListItemBinding
) : RecyclerView.ViewHolder(viewBinding.root)

@Singleton
interface UserClickListener {
    fun onUserClicked(user: GithubUser)
}