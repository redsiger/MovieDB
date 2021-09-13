package com.example.androidschool.moviedb.screens.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidschool.moviedb.MovieApp
import com.example.androidschool.moviedb.databinding.FragmentStartBinding
import com.example.androidschool.moviedb.network.MovieService
import com.example.androidschool.moviedb.network.MoviesListener
import com.example.androidschool.moviedb.network.response.Movie
import com.example.androidschool.moviedb.screens.adapter.start.StartFragmentAdapter
import com.example.androidschool.moviedb.screens.adapter.start.StartFragmentAdapter2
import kotlin.random.Random

class StartFragment : Fragment(), LifecycleObserver {

    private var _binding: FragmentStartBinding? = null
    private val mBinding get() = _binding!!
    lateinit var mAdapter : StartFragmentAdapter
    lateinit var mViewModel: StartFragmentViewModel
    lateinit var mMovieListObserver: Observer<List<Movie>>
    lateinit var mMovieListLoadedObserver: Observer<Boolean>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(inflater, container, false)

        val view = mBinding.root

        initialize()
        return (view)
    }

    private fun refreshMovieList() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onStart() {
        super.onStart()
    }

    private fun initialize() {
        mBinding.startFragmentMoviesRefresh.setOnRefreshListener {
            val queryStringsList = listOf<String>("Naruto", "Star Wars", "Sex Education", "Marvel")
            val queryString = queryStringsList[(0..queryStringsList.size-1).random()]
            mViewModel.searchMovies(queryString)
//            mBinding.startFragmentSearchInput.setText(queryString)
            mBinding.startFragmentMoviesRefresh.isRefreshing = false
        }


        val layoutManager = LinearLayoutManager(this.requireContext())
        mBinding.recyclerMovieList.layoutManager = layoutManager
        mViewModel = ViewModelProvider(this.requireActivity()).get(StartFragmentViewModel::class.java)
        mAdapter = StartFragmentAdapter(this.requireContext())
        mBinding.recyclerMovieList.adapter = mAdapter

//

        mMovieListObserver = Observer {
            mAdapter.setList(it)
        }
        mMovieListLoadedObserver = Observer {
            when (it) {
                true -> {
                    mBinding.progressBarFragmentStart.visibility = View.GONE
                    mBinding.recyclerMovieList.visibility = View.VISIBLE
                }
                false -> {
                    mBinding.recyclerMovieList.visibility = View.GONE
                    mBinding.progressBarFragmentStart.visibility = View.VISIBLE
                }
            }
        }

        mViewModel.isMoviesLoaded.observe(viewLifecycleOwner, mMovieListLoadedObserver)

        mViewModel.recyclerListData.observe(viewLifecycleOwner, mMovieListObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding.recyclerMovieList.adapter = null
        _binding = null
        mViewModel.recyclerListData.removeObserver(mMovieListObserver)
    }
}