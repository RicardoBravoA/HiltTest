package com.rba.hilttest.data.api

import com.rba.hilttest.data.response.MoviePopularResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

fun interface MovieApi {

    @GET("3/movie/popular")
    suspend fun popularMovies(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Response<MoviePopularResponse>

}