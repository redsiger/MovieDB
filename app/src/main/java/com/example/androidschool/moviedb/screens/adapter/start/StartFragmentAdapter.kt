package com.example.androidschool.moviedb.screens.adapter.start

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidschool.moviedb.R
import com.example.androidschool.moviedb.databinding.FragmentRecyclerMovieItemBinding
import com.example.androidschool.moviedb.network.response.Movie
import com.example.androidschool.moviedb.network.response.MovieSearchResponse

class StartFragmentAdapter(
    private val context: Context,
    private val movieList: List<Movie>
) : RecyclerView.Adapter<StartFragmentAdapter.MovieSearchResponseHolder>() {

    class MovieSearchResponseHolder(view : View) : RecyclerView.ViewHolder(view) {
        val mBinding = FragmentRecyclerMovieItemBinding.bind(view)
        fun bind(movie: Movie) {
            mBinding.itemMovieTitle.text = movie.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchResponseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_recycler_movie_item, parent, false)
        return MovieSearchResponseHolder(view)
    }

    override fun onBindViewHolder(holder: MovieSearchResponseHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}