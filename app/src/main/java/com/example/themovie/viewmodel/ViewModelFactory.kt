package com.example.themovie.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themovie.model.api.ApiHelper
import com.example.themovie.repository.MovieRepository

/**
 * Created by aizat on 27/4/2021
 */
class ViewModelFactory(private val apiHelper: ApiHelper, private val bundle: Bundle?) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(MovieRepository(apiHelper)) as T
        } else if (modelClass.isAssignableFrom(MovieSingleViewModel::class.java)) {
            return MovieSingleViewModel(MovieRepository(apiHelper), bundle) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}