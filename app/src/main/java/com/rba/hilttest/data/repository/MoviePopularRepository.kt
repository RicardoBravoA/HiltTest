package com.rba.hilttest.data.repository

import com.rba.hilttest.data.util.Failure
import com.rba.hilttest.data.util.ResultType
import com.rba.hilttest.domain.model.MovieModel

fun interface MoviePopularRepository {

    suspend fun getPopularMovies(page: Int): ResultType<List<MovieModel>, Failure>

}
