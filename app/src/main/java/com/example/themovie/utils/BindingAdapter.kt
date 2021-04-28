package com.example.themovie.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.themovie.model.entities.Genre

/**
 * Created by aizat on 27/4/2021
 */
@BindingAdapter("posterImage")
fun loadImage(view: ImageView, path: String?) {
    if (!path.isNullOrEmpty()) {
        Glide
            .with(view.context)
            .load("https://image.tmdb.org/t/p/w500$path")
            .into(view)
    }
}

@BindingAdapter("genres")
fun combineGenre(view: TextView, genres: List<Genre>?) {
    if (!genres.isNullOrEmpty()) {
        view.text = genres.joinToString(", ") {
            it.name
        }
    }
}