package com.example.androidschool.moviedb.network.response


import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

