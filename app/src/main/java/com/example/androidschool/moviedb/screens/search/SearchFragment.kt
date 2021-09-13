package com.example.androidschool.moviedb.screens.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidschool.moviedb.R
import com.example.androidschool.moviedb.databinding.FragmentSearchBinding
import com.example.androidschool.moviedb.databinding.FragmentStartBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        initialization()
        return (mBinding.root)
    }

    private fun initialization() {
        mBinding.startFragmentSearchButton.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}