package com.example.themovie.model.api

import com.example.themovie.model.entities.Movie
import com.example.themovie.model.entities.MovieList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by aizat on 27/4/2021
 */
interface ApiService {

    @GET("now_playing")
    suspend fun getMovies(
        @Query("api_key") api_key: String = ApiConstants.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: String,
    ): MovieList

    @GET("{movieId}?api_key=" + ApiConstants.API_KEY)
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): Movie

}

object ApiConstants {
    const val API_KEY = "89a571acaf96541bdee2b19060fc9980"
}