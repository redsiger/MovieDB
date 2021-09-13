package com.example.androidschool.moviedb.screens.start

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androidschool.moviedb.network.MovieApi
import com.example.androidschool.moviedb.network.response.Movie
import com.example.androidschool.moviedb.network.response.MovieSearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class StartFragmentViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var totalMoviesResult : MutableList<Movie>
    lateinit var recyclerListData : MutableLiveData<List<Movie>>
    lateinit var isMoviesLoaded : MutableLiveData<Boolean>
    lateinit var apiService : MovieApi
    var currentPage : Int = 0
    var totalPages : Int = 0
    var currentResults : Int = 0
    var totalResults : Int = 0


    init {
        totalMoviesResult = mutableListOf()
        recyclerListData = MutableLiveData()
        isMoviesLoaded = MutableLiveData()
        apiService = MovieApi()
        GlobalScope.launch(Dispatchers.IO) {
//            delay(2000)
            getPopularMovieList()
        }
    }

    public fun isMovieListLoaded() : Boolean {
        var result : Boolean = false
        if (isMoviesLoaded.value == true) {
            result = true
        }
        return result
    }

    fun searchMovies(queryString: String){
        isMoviesLoaded.postValue(false)
        apiService
            .getSearchResult(queryString)
            .enqueue(object: retrofit2.Callback<MovieSearchResponse> {
                override fun onResponse(call: Call<MovieSearchResponse>, response: Response<MovieSearchResponse>) {
                    if (response.isSuccessful) {
                        val movieSearchResponse = response.body() as MovieSearchResponse
                        val movies = movieSearchResponse.results
                        totalMoviesResult = mutableListOf()
                        movies.map {
                            totalMoviesResult.add(it)
                        }
                        recyclerListData.postValue(totalMoviesResult)
                        isMoviesLoaded.postValue(true)

                        currentPage = movieSearchResponse.page
                        totalPages = movieSearchResponse.totalPages
                        currentResults = movies.size
                        totalResults = movieSearchResponse.totalResults
                    } else {
                        recyclerListData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                    Log.e("ERROR", "Something going wrong... ${t.message}")
                }
            })
    }

    private fun getPopularMovieList() {
        isMoviesLoaded.postValue(false)
        apiService
            .getMovieList()
            .enqueue(object: retrofit2.Callback<MovieSearchResponse> {
            override fun onResponse(call: Call<MovieSearchResponse>, response: Response<MovieSearchResponse>) {
                if (response.isSuccessful) {
                    val movieSearchResponse = response.body() as MovieSearchResponse
                    val movies = movieSearchResponse.results
                    movies.map {
                        totalMoviesResult.add(it)
                    }
                    recyclerListData.postValue(totalMoviesResult)
                    isMoviesLoaded.postValue(true)
                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                Log.e("ERROR", "Something going wrong... ${t.message}")
            }
        })
    }
}