package com.example.androidschool.moviedb.screens.start

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidschool.moviedb.databinding.FragmentStartBinding
import com.example.androidschool.moviedb.network.MovieApi
import com.example.androidschool.moviedb.network.response.MovieSearchResponse
import com.example.androidschool.moviedb.screens.adapter.start.StartFragmentAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class StartFragment : Fragment(), LifecycleObserver {

    private var _binding: FragmentStartBinding? = null
    private val mBinding get() = _binding!!
    lateinit var mAdapter : StartFragmentAdapter
    lateinit var mViewModel: StartFragmentViewModel

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
        mViewModel = ViewModelProvider(this).get(StartFragmentViewModel::class.java)

        mViewModel.isMoviesLoaded.observe(this, Observer {
            if (it == true) {
                mBinding.progressBar.visibility = View.GONE
            }
        })

        mViewModel.recyclerListData.observe(this, Observer {
            mAdapter = StartFragmentAdapter(this.requireContext(), it)
            mAdapter.notifyDataSetChanged()
            mBinding.recyclerMovieList.adapter = mAdapter
        })

//        GlobalScope.launch(Dispatchers.IO) {
//            GlobalScope.launch(Dispatchers.Main) {
//                mBinding.progressBar.visibility = View.GONE
//            }
//        }
    }


    //    private fun getAllMovieList() {
//        val apiService = MovieApi().getMovieList()
//        apiService.enqueue(object: retrofit2.Callback<MovieSearchResponse> {
//            override fun onResponse(
//                call: Call<MovieSearchResponse>,
//                response: Response<MovieSearchResponse>
//            ) {
//                val movieSearchResponse = response.body() as MovieSearchResponse
//                val movies = movieSearchResponse.results as MutableList
//                mAdapter = StartFragmentAdapter(this@StartFragment.requireContext(), movies)
//                mAdapter.notifyDataSetChanged()
//                mBinding.recyclerMovieList.adapter = mAdapter
//            }
//
//            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
//                Log.e("ERROR", "Something going wrong... ${t.message}")
//            }
//        })
//    }


}