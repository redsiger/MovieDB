package com.example.androidschool.moviedb.network

import android.util.Log
import com.example.androidschool.moviedb.network.response.Movie
import com.example.androidschool.moviedb.network.response.MovieSearchResponse
import okhttp3.internal.userAgent
import retrofit2.Call
import retrofit2.Response
import java.util.*

typealias MoviesListener = (movieList: List<Movie>) -> Unit

class MovieService(movieApi: MovieApi) {
    private var movieList = mutableListOf<Movie>()
    private val listeners = mutableSetOf<MoviesListener>()


    init {
        getAllMovieList()
    }

    fun getPopularMovies(): List<Movie> {
        return movieList
    }

    fun deleteMovie(movie: Movie) {
        val indexToDelete = movieList.indexOfFirst { it.id == movie.id }
        if (indexToDelete != -1) {
            movieList.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun moveMovie(movie: Movie, moveBy: Int) {
        val oldIndex = movieList.indexOfFirst { it.id == movie.id }
        if (oldIndex != -1) {
            val newIndex = oldIndex + moveBy
            if (newIndex < 0 || newIndex >= movieList.size) return
            Collections.swap(movieList, oldIndex, newIndex)
            notifyChanges()
        }
    }

    fun addListener(listener: MoviesListener) {
        listeners.add(listener)
        listener(movieList)
    }

    fun removeListener(listener: MoviesListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach {it(movieList)}
    }

    private fun getAllMovieList() {
        val apiService = MovieApi().getMovieList()
        apiService.enqueue(object: retrofit2.Callback<MovieSearchResponse> {
            override fun onResponse(call: Call<MovieSearchResponse>, response: Response<MovieSearchResponse>) {
                if (response.isSuccessful) {
                    val movieSearchResponse = response.body() as MovieSearchResponse
                    val movies = movieSearchResponse.results
                    movieList = movies as MutableList<Movie>
                    notifyChanges()
                } else {

                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                Log.e("ERROR", "Something going wrong... ${t.message}")
            }
        })
    }
}