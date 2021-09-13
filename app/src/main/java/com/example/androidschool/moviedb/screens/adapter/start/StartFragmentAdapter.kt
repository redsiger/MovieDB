package com.example.androidschool.moviedb.screens.adapter.start

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidschool.moviedb.R
import com.example.androidschool.moviedb.databinding.FragmentRecyclerMovieItemBinding
import com.example.androidschool.moviedb.databinding.FragmentRecyclerMovieItemsLoadingBinding
import com.example.androidschool.moviedb.network.TMBD_IMG_URL
import com.example.androidschool.moviedb.network.response.Movie
import com.example.androidschool.moviedb.network.response.MovieSearchResponse
import com.squareup.picasso.Picasso

const val VIEW_TYPE_ITEM = 0
const val VIEW_TYPE_LOADING = 0

class StartFragmentAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movieList: List<Movie> = emptyList()

    fun setList(list: List<Movie>) {
        movieList = list
        notifyDataSetChanged()
    }


    class MovieSearchResponseHolder(view : View) : RecyclerView.ViewHolder(view) {
        val mBinding = FragmentRecyclerMovieItemBinding.bind(view)

        fun bind(movie: Movie) = with(mBinding) {
            itemMovieTitle.text = movie.title
            itemMovieYear.text = movie.releaseDate.substring(0, 4)
            itemMovieDesc.text = movie.overview

            if (movie.posterPath != null) {
                Picasso.get()
                    .load(TMBD_IMG_URL + movie.posterPath)
                    .placeholder(R.drawable.movie_img_loading_anim)
                    .resizeDimen(R.dimen.item_movie_img_width, R.dimen.item_movie_img_height)
                    .into(itemMovieImg)
            } else {
                itemMovieImg.setImageResource(R.drawable.ic_loading_failed)
            }

        }

    }

    class LoadingHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val mBinding = FragmentRecyclerMovieItemsLoadingBinding.bind(view)
        fun showLoading() {}
    }

    override fun getItemViewType(position: Int): Int {
        return if (movieList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_recycler_movie_item, parent, false)
            return MovieSearchResponseHolder(view)
        } else {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_recycler_movie_items_loading, parent, false)
                return LoadingHolder(view)
            }
    }


    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is MovieSearchResponseHolder) {
            holder.bind(movieList[position])
        } else
            if (holder is LoadingHolder) {
                holder.showLoading()
        }
    }
}