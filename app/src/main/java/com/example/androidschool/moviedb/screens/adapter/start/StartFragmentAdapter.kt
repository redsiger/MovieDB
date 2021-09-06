package com.example.androidschool.moviedb.screens.adapter.start

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidschool.moviedb.R
import com.example.androidschool.moviedb.databinding.FragmentRecyclerMovieItemBinding
import com.example.androidschool.moviedb.network.TMBD_IMG_URL
import com.example.androidschool.moviedb.network.response.Movie
import com.example.androidschool.moviedb.network.response.MovieSearchResponse
import com.squareup.picasso.Picasso

class StartFragmentAdapter(
    private val context: Context
) : RecyclerView.Adapter<StartFragmentAdapter.MovieSearchResponseHolder>() {

    private var movieList: List<Movie> = emptyList()

    fun setList(list: List<Movie>) {
        movieList = list
        notifyDataSetChanged()
    }

    class MovieSearchResponseHolder(view : View) : RecyclerView.ViewHolder(view) {
        val mBinding = FragmentRecyclerMovieItemBinding.bind(view)

        fun bind(movie: Movie) = with(mBinding) {
            itemMovieTitle.text = movie.title
            itemMovieYear.text = movie.releaseDate
            itemMovieDesc.text = movie.overview


            Picasso.get().load(TMBD_IMG_URL + movie.posterPath).resizeDimen(R.dimen.item_movie_img_width, R.dimen.item_movie_img_height).into(itemMovieImg)
        }

        private fun trimDesc(string: String) : String {
            if (string.length <= 150) {
                return string
            }
            return string.substring(0, 147) + "..."
        }

        private fun trimDate(string: String) :String {
            return string.substring(0, 4)
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