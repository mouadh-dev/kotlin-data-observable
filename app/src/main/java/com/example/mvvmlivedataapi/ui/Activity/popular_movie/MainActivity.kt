package com.example.mvvmlivedataapi.ui.Activity.popular_movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mvvmlivedataapi.data.api.TheMovieDBClient
import com.example.mvvmlivedataapi.data.api.TheMovieDBInterface
import com.example.mvvmlivedataapi.data.repository.MoviePagedListRepository
import com.example.mvvmlivedataapi.data.repository.NetworkState
import com.example.mvvmlivedataapi.databinding.ActivityMainBinding
import com.example.mvvmlivedataapi.ui.Activity.single_movie_details.SingleMovieActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel:MainActivityViewModel
    lateinit var movieRepository: MoviePagedListRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        initView()
        setContentView(view)


    }
    private fun getViewModel():MainActivityViewModel {
        return ViewModelProviders.of(this, object:ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }

    private fun initView() {
        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MoviePagedListRepository(apiService)
        viewModel = getViewModel()
        val movieAdapter = PopularMoviePagedListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this,3)

        gridLayoutManager.spanSizeLookup = object:GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3
            }
        }
        binding.rvMovieList.layoutManager = gridLayoutManager
        binding.rvMovieList.setHasFixedSize(true)
        binding.rvMovieList.adapter = movieAdapter

        viewModel.moviePageList.observe(this, Observer{
            movieAdapter.submitList(it)
        })
        viewModel.networkState.observe(this, Observer{
            binding.progressBarPopular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.errorTextPopular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.FAILED) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()){
                movieAdapter.setNetworkState(it)
            }
        })
    }
}