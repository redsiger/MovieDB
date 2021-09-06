package com.example.androidschool.moviedb.screens

import com.example.androidschool.moviedb.network.response.Movie

interface Navigator {

    fun showDetails(movie: Movie)

    fun goBack()

    fun snack(messageRes: Int)
}