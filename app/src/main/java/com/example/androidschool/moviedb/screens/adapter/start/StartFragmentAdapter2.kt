package com.example.androidschool.moviedb.screens.adapter.start

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidschool.moviedb.R
import com.example.androidschool.moviedb.databinding.FragmentRecyclerMovieItemBinding
import com.example.androidschool.moviedb.network.TMBD_IMG_URL
import com.example.androidschool.moviedb.network.response.Movie
import com.squareup.picasso.Picasso

class StartFragmentAdapter2: RecyclerView.Adapter<StartFragmentAdapter2.MovieViewHolder>() {

    var movies: List<Movie> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MovieViewHolder(
        val binding: FragmentRecyclerMovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentRecyclerMovieItemBinding.inflate(inflater, parent ,false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        with(holder.binding) {
            itemMovieTitle.text = movie.title
            itemMovieDesc.text = movie.overview
            itemMovieYear.text = movie.releaseDate.substring(0, 4)
            Picasso.get().load(TMBD_IMG_URL + movie.posterPath).resizeDimen(R.dimen.item_movie_img_width, R.dimen.item_movie_img_height).into(itemMovieImg)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}