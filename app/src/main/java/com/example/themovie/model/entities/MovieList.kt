package com.example.themovie.model.entities

/**
 * Created by aizat on 27/4/2021
 */
data class MovieList(
    val dates: DateModel,
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int,
)