package com.example.themovie.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.themovie.model.entities.Movie
import com.example.themovie.repository.MovieRepository
import com.example.themovie.utils.Resource
import kotlinx.coroutines.Dispatchers

/**
 * Created by aizat on 27/4/2021
 */
class MovieSingleViewModel(private val movieRepository: MovieRepository, private val bundle: Bundle?) : ViewModel() {

    val movieLive: MutableLiveData<Movie> by lazy {
        MutableLiveData()
    }

    // this function will return details of movies
    fun getMovie() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val movieId = bundle?.getInt(MOVIE_ID)
            if (movieId == null) {
                emit(Resource.error(data = null, message = "Movie Id not found"))
            } else {
                emit(Resource.success(data = movieRepository.getMovieDetail(movieId)))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    companion object {
        val MOVIE_ID = "movie_id"
    }
}