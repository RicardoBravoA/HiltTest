package com.rba.hilttest.data.datasource

import com.rba.hilttest.data.api.MovieApi
import com.rba.hilttest.data.datasource.di.IoDispatcher
import com.rba.hilttest.data.util.Failure
import com.rba.hilttest.data.util.ResultType
import com.rba.hilttest.domain.mapper.toDomain
import com.rba.hilttest.domain.model.MovieModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val movieApi: MovieApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieDataSource {

    override suspend fun getPopularMovies(page: Int): ResultType<List<MovieModel>, Failure> {
        return withContext(dispatcher) {
            try {
                val result = movieApi.popularMovies(page, "en-US")
                if (result.isSuccessful) {
                    result.body()?.let { response ->
                        ResultType.Success(response.results.map {
                            it.toDomain()
                        })
                    } ?: run {
                        ResultType.Error(Failure.UnknownError)
                    }
                } else {
                    ResultType.Error(Failure.ServerError)
                }
            } catch (e: Exception) {
                ResultType.Error(Failure.NetworkError)
            }
        }
    }

}
