package com.example.androidschool.moviedb.screens.start

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidschool.moviedb.network.MovieApi
import com.example.androidschool.moviedb.network.response.Movie
import com.example.androidschool.moviedb.network.response.MovieSearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class StartFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var _recyclerListData : MutableLiveData<List<Movie>> = MutableLiveData()
    lateinit var recyclerListData : MutableLiveData<List<Movie>>
    lateinit var isMoviesLoaded : MutableLiveData<Boolean>
    lateinit var apiService : MovieApi

    init {
        recyclerListData = MutableLiveData()
        isMoviesLoaded = MutableLiveData()
        apiService = MovieApi()
        GlobalScope.launch(Dispatchers.IO) {
            getAllMovieList()
        }
    }

    private fun isMovieListLoaded() {
        isMoviesLoaded.postValue(true)
    }

    fun searchMovies(queryString: String){
        apiService
            .getSearchResult(queryString)
            .enqueue(object: retrofit2.Callback<MovieSearchResponse> {
                override fun onResponse(call: Call<MovieSearchResponse>, response: Response<MovieSearchResponse>) {
                    if (response.isSuccessful) {
                        val movieSearchResponse = response.body() as MovieSearchResponse
                        val movies = movieSearchResponse.results
                        recyclerListData.postValue(movies)
                        isMovieListLoaded()
                    } else {
                        recyclerListData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                    Log.e("ERROR", "Something going wrong... ${t.message}")
                }
            })
    }

    private fun getAllMovieList() {
        apiService
            .getMovieList()
            .enqueue(object: retrofit2.Callback<MovieSearchResponse> {
            override fun onResponse(call: Call<MovieSearchResponse>, response: Response<MovieSearchResponse>) {
                if (response.isSuccessful) {
                    val movieSearchResponse = response.body() as MovieSearchResponse
                    val movies = movieSearchResponse.results
                    recyclerListData.postValue(movies)
                    isMovieListLoaded()
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