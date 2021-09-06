package com.example.androidschool.moviedb

import android.app.Application
import com.example.androidschool.moviedb.network.MovieApi
import com.example.androidschool.moviedb.network.MovieService

class MovieApp: Application() {

    val movieApi = MovieApi()
    val movieService = MovieService(movieApi)
}