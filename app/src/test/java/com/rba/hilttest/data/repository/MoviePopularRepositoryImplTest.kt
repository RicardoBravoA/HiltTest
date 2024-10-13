package com.rba.hilttest.data.repository

import com.rba.hilttest.base.BaseMockk
import com.rba.hilttest.data.datasource.MovieDataSource
import com.rba.hilttest.data.util.Failure
import com.rba.hilttest.data.util.ResultType
import com.rba.hilttest.domain.model.MovieModel
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class MoviePopularRepositoryImplTest : BaseMockk() {

    @InjectMockKs
    private lateinit var repository: MoviePopularRepositoryImpl

    @MockK
    lateinit var dataSource: MovieDataSource

    private val page = 1

    @Test
    fun `given params when call invoke then return success result`() = runBlocking {
        // given
        val expected = mockk<List<MovieModel>>()

        coEvery { dataSource.getPopularMovies(page) } returns ResultType.Success(expected)

        // when
        val result = repository.getPopularMovies(page)
        result as ResultType.Success

        // then
        assertEquals(expected, result.value)
    }

    @Test
    fun `given params when call invoke then return failure result`() = runBlocking {
        // given
        val expected = mockk<Failure>()

        coEvery { dataSource.getPopularMovies(page) } returns ResultType.Error(expected)

        // when
        val result = repository.getPopularMovies(page)
        result as ResultType.Error

        // then
        assertEquals(expected, result.value)
    }

}
