package com.rba.hilttest.data.repository

import com.rba.hilttest.data.util.Failure
import com.rba.hilttest.data.util.ResultType
import com.rba.hilttest.domain.model.MovieModel

interface MoviePopularRepository {

    suspend fun getPopularMovies(page: Int): ResultType<List<MovieModel>, Failure>

}
