package com.example.mvvmlivedataapi.data.repository

import androidx.lifecycle.LiveData
import com.example.mvvmlivedataapi.data.api.TheMovieDBInterface
import com.example.mvvmlivedataapi.data.entities.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsrepository(private val apiService: TheMovieDBInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource
    fun fetchingSingleMovieDetails(compositeDisposable: CompositeDisposable,movieId: Int): LiveData<MovieDetails>{
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)
        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }
    fun getMovieDetailsNetworkState(): LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }
}