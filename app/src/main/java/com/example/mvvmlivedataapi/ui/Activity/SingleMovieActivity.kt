package com.example.mvvmlivedataapi.ui.Activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.mvvmlivedataapi.data.api.POSTER_BASE_URL
import com.example.mvvmlivedataapi.data.api.TheMovieDBClient
import com.example.mvvmlivedataapi.data.api.TheMovieDBInterface
import com.example.mvvmlivedataapi.data.repository.MovieDetailsrepository
import com.example.mvvmlivedataapi.data.repository.NetworkState
import com.example.mvvmlivedataapi.data.entities.MovieDetails
import com.example.mvvmlivedataapi.databinding.ActivitySingleMovieBinding
import java.text.NumberFormat
import java.util.*

class SingleMovieActivity : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsrepository
    private lateinit var binding: ActivitySingleMovieBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleMovieBinding.inflate(layoutInflater)
        val view = binding.root
        initView()
        setContentView(view)


    }

    private fun initView() {
        val movieId:Int = intent.getIntExtra("id", 1)
        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsrepository(apiService)
        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer{
            bindUI(it)
        })
        viewModel.networkState.observe(this, Observer{
            binding.progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.errorText.visibility = if (it == NetworkState.FAILED) View.VISIBLE else View.GONE
        })
    }

    @SuppressLint("SetTextI18n")
    private fun bindUI(it: MovieDetails?) {

        binding.movieTitle.text = it!!.title
        binding.movieTagline.text = it.tagline
        binding.movieReleaseDate.text = it.releaseDate
        binding.movieRating.text = it.rating.toString()
        binding.movieRunTime.text = it.runtime.toString() + " minutes"
        binding.movieOverview.text = it.overview

        val formatCurrency: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        binding.movieBudget.text = formatCurrency.format(it.budget)
        binding.movieRevenue.text = formatCurrency.format(it.revenue)

        val moviePosterUrl: String = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterUrl)
            .into(binding.moviePoster)


    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T: ViewModel> create(modelClass: Class<T>): T{
                return SingleMovieViewModel(movieRepository,movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}