package com.example.mvvmlivedataapi.data.api

import com.example.mvvmlivedataapi.data.entities.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDBInterface {
    // https://api.themoviedb.org/3/movie/popular?api_key=9ce1dfdcd07a245fe0ff396da28aeb55
    // https://api.themoviedb.org/3/movie/736526?api_key=9ce1dfdcd07a245fe0ff396da28aeb55
    // https://api.themoviedb.org/3/

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}