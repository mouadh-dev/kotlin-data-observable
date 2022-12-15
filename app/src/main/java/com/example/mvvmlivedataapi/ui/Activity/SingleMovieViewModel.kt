package com.example.mvvmlivedataapi.ui.Activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmlivedataapi.data.repository.MovieDetailsrepository
import com.example.mvvmlivedataapi.data.repository.NetworkState
import com.example.mvvmlivedataapi.data.entities.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieRepository: MovieDetailsrepository,movieId: Int): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchingSingleMovieDetails(compositeDisposable,movieId)
    }
    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }
    override fun onCleared(){
        super.onCleared()
        compositeDisposable.clear()
    }
}