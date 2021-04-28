package com.example.themovie.model.api

/**
 * Created by aizat on 27/4/2021
 */
class ApiHelper(private val apiService: ApiService) {

    suspend fun getMovies(page: String) = apiService.getMovies(page = page)

    suspend fun getMovieDetail(movieId: Int) = apiService.getMovieDetail(movieId)
}