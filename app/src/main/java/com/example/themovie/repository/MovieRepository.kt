package com.example.themovie.repository

import com.example.themovie.model.api.ApiHelper

/**
 * Created by aizat on 27/4/2021
 */
class MovieRepository (private val apiHelper: ApiHelper) {

    suspend fun getMovies(page: String) = apiHelper.getMovies(page)

    suspend fun getMovieDetail(movieId: Int) = apiHelper.getMovieDetail(movieId)
}