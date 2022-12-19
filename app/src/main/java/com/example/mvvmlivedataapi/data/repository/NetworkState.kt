package com.example.mvvmlivedataapi.data.repository

enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val msg: String) {
    companion object{
        val LOADED: NetworkState
        val LOADING: NetworkState
        val FAILED: NetworkState
        val END_OF_LIST: NetworkState
        init {
            LOADED = NetworkState(Status.SUCCESS, "success")
            LOADING = NetworkState(Status.RUNNING, "running")
            FAILED = NetworkState(Status.FAILED, "something went wrong")
            END_OF_LIST = NetworkState(Status.FAILED, "you have reached the end")
        }
    }
}