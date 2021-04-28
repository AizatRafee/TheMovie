package com.example.themovie.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovie.holder.MovieHolder
import com.example.themovie.model.entities.Movie
import com.example.themovie.utils.SingleLiveEvent

/**
 * Created by aizat on 27/4/2021
 */
class MovieAdapter(var movies: ArrayList<Movie>) : RecyclerView.Adapter<MovieHolder>() {

    val clickEvent = SingleLiveEvent<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        movies.get(position).let {
            holder.bindTo(it, clickEvent)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun addMovie(movies: List<Movie>) {
        this.movies.apply {
            clear()
            addAll(movies)
        }
    }
}