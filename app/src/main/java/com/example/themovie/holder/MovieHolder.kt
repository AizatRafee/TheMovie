package com.example.themovie.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.themovie.R
import com.example.themovie.databinding.ItemMovieBinding
import com.example.themovie.model.entities.Movie
import com.example.themovie.utils.SingleLiveEvent

/**
 * Created by aizat on 27/4/2021
 */
class MovieHolder(private val itemMovieBinding: ItemMovieBinding) : RecyclerView.ViewHolder(itemMovieBinding.root) {

    companion object {
        fun create(parent: ViewGroup): MovieHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val viewDataBinding: ItemMovieBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_movie, parent, false
            )

            return MovieHolder(viewDataBinding)
        }
    }

    fun bindTo(
        model: Movie,
        clickEvent: SingleLiveEvent<Int>
    ) {
        itemMovieBinding.movie = model

        itemMovieBinding.root.setOnClickListener{
            clickEvent.postValue(model.id)
        }
    }
}