package com.example.mvvmlivedataapi.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mvvmlivedataapi.data.api.FIRST_PAGE
import com.example.mvvmlivedataapi.data.api.TheMovieDBInterface
import com.example.mvvmlivedataapi.data.entities.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private val apiService: TheMovieDBInterface, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int,Movie>() {
    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                      if(it.totalPages >= params.key){
                          callback.onResult(it.movieList,  params.key+1)
                          networkState.postValue(NetworkState.LOADED)
                      }else {
                          networkState.postValue(NetworkState.END_OF_LIST)
                      }
                    },{
                        networkState.postValue(NetworkState.FAILED)
                        Log.e("mouadh said error here",it.message!!)

                    }
                )

        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getPopularMovie(page)
               .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                    callback.onResult(it.movieList, null, page+1)
                        networkState.postValue(NetworkState.LOADED)
                    },{
                        networkState.postValue(NetworkState.FAILED)
                        Log.e("mouadh said error here", it.message!!)

                    }
                )

        )
    }
}