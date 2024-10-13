package com.rba.hilttest.data.repository

import com.rba.hilttest.data.datasource.MovieDataSource
import com.rba.hilttest.data.util.Failure
import com.rba.hilttest.data.util.ResultType
import com.rba.hilttest.domain.model.MovieModel
import javax.inject.Inject

class MoviePopularRepositoryImpl @Inject constructor(private val dataSource: MovieDataSource) :
    MoviePopularRepository {

    override suspend fun getPopularMovies(page: Int): ResultType<List<MovieModel>, Failure> {
        return dataSource.getPopularMovies(page)
    }

}
