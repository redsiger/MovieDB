package com.example.androidschool.moviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.androidschool.moviedb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mNavController : NavController
    private var _binding : ActivityMainBinding? = null
    val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }
}