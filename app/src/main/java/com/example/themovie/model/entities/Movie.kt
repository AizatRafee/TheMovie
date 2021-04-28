package com.example.themovie.model.entities

/**
 * Created by aizat on 27/4/2021
 */
data class Movie(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Float,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val imdb_id: String?,
    val production_companies: List<Company>?,
    val production_countries: List<Country>?,
    val revenue: Int,
    val runtime: Int?,
    val spoken_languages: List<Language>,
    val status: String,
    val tagline: String?
)