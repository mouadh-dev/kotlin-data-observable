package com.example.mvvmlivedataapi.ui.Activity.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mvvmlivedataapi.data.entities.Movie
import com.example.mvvmlivedataapi.data.repository.MoviePagedListRepository
import com.example.mvvmlivedataapi.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movieRepository: MoviePagedListRepository): ViewModel()  {
    private val compositeDisposable = CompositeDisposable()
    val moviePageList:LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }
    val networkState:LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }
    fun listIsEmpty():Boolean{
        return moviePageList.value?.isEmpty() ?: true
    }
    fun OnCleared(){
        super.onCleared()
        compositeDisposable.dispose()
    }
}