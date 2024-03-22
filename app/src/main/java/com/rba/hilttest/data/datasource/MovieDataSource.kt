package com.rba.hilttest.data.datasource

import com.rba.hilttest.data.util.Failure
import com.rba.hilttest.data.util.ResultType
import com.rba.hilttest.domain.model.MovieModel

fun interface MovieDataSource {

    suspend fun getPopularMovies(page: Int): ResultType<List<MovieModel>, Failure>

}
