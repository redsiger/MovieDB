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

class StartFragment : Fragment(), LifecycleObserver {

    private var _binding: FragmentStartBinding? = null
    private val mBinding get() = _binding!!
    lateinit var mAdapter : StartFragmentAdapter
    lateinit var mViewModel: StartFragmentViewModel
    lateinit var mMovieListObserver: Observer<List<Movie>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(inflater, container, false)

        val view = mBinding.root
        return (view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onStart() {
        super.onStart()
        val layoutManager = LinearLayoutManager(this.requireContext())
        mBinding.recyclerMovieList.layoutManager = layoutManager
        mViewModel = ViewModelProvider(this.requireActivity()).get(StartFragmentViewModel::class.java)
        mAdapter = StartFragmentAdapter(this.requireContext())
        mBinding.recyclerMovieList.adapter = mAdapter

        mBinding.startFragmentSearchButton.setOnClickListener {
            val queryString :String = mBinding.startFragmentSearchInput.text.toString()
            mViewModel.searchMovies(queryString)
        }

        mMovieListObserver = Observer {
            mAdapter.setList(it)
        }

        mViewModel.isMoviesLoaded.observe(this, Observer {
            if (it == true) {
                mBinding.progressBarFragmentStart.visibility = View.GONE
            }
        })

        mViewModel.recyclerListData.observe(viewLifecycleOwner, mMovieListObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mBinding.recyclerMovieList.adapter = null
        mViewModel.recyclerListData.removeObserver(mMovieListObserver)
    }
}