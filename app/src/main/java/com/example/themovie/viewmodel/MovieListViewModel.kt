package com.example.themovie.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.themovie.model.entities.Movie
import com.example.themovie.model.entities.MovieList
import com.example.themovie.repository.MovieRepository
import com.example.themovie.utils.Resource
import kotlinx.coroutines.Dispatchers

/**
 * Created by aizat on 27/4/2021
 */
class MovieListViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    var page = 0
    var total_pages = 1
    var movies: ArrayList<Movie> = ArrayList()

    // this function will return list of movies
    fun getMovies() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            if (page < total_pages) {
                emit(Resource.success(data = movieRepository.getMovies((page + 1).toString())))
            } else {
                emit(Resource.error(data = null, message = "End of movie list"))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun updateMovieList(moviePage: MovieList) {
        page = moviePage.page
        total_pages = moviePage.total_pages
        movies.addAll(moviePage.results)
    }

    fun refreshMovieList() {
        page = 0
        total_pages = 1
    }

}