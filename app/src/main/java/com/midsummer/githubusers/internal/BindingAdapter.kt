package com.midsummer.githubusers.internal

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.midsummer.githubusers.R

/**
 * Created by nienle on 03,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(100, 100)
    Glide.with(imageView)
        .load(url)
        .apply(requestOptions)
        .placeholder(R.drawable.placeholder_ava_rounded)
        .fallback(R.drawable.placeholder_ava_rounded)
        .into(imageView)
}