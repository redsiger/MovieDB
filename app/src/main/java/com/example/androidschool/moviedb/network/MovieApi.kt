package com.example.androidschool.moviedb.network

import com.example.androidschool.moviedb.network.response.MovieSearchResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "bfe801649ca860d496a1b7a533405418"

interface MovieApi {


//    https://api.themoviedb.org/3/movie/popular?api_key=bfe801649ca860d496a1b7a533405418
    @GET("movie/popular")
    fun getMovieList() : Call<MovieSearchResponse>

    companion object {
        operator fun invoke() : MovieApi {

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val addApiKeyInterceptor = Interceptor {
                val url = it.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                val reqest = it.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor it.proceed(reqest)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(addApiKeyInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApi::class.java)

            return retrofit
        }
    }
}