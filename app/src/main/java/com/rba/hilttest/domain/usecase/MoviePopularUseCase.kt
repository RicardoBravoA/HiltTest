package com.rba.hilttest.domain.usecase

import com.rba.hilttest.data.repository.MoviePopularRepository
import com.rba.hilttest.data.util.Failure
import com.rba.hilttest.data.util.ResultType
import com.rba.hilttest.domain.model.MovieModel
import javax.inject.Inject

class MoviePopularUseCase @Inject constructor(private val repository: MoviePopularRepository) {

    suspend operator fun invoke(page: Int): ResultType<List<MovieModel>, Failure> {
        return repository.getPopularMovies(page)
    }

}
