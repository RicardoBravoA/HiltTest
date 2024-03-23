package com.rba.hilttest.data.datasource

import com.rba.hilttest.base.BaseDataSource
import com.rba.hilttest.data.api.MovieApi
import com.rba.hilttest.data.response.MoviePopularItemResponse
import com.rba.hilttest.data.response.MoviePopularResponse
import com.rba.hilttest.data.util.Failure
import com.rba.hilttest.data.util.ResultType
import com.rba.hilttest.domain.mapper.toDomain
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class MovieDataSourceImplTest : BaseDataSource() {

    @InjectMockKs
    private lateinit var dataSource: MovieDataSourceImpl

    @MockK
    private lateinit var api: MovieApi
    private val page = 1
    private val language = "en-US"

    @Test
    fun `given params when call getPopularMovies then return success result`() = runBlocking {
        //given
        val response = MoviePopularResponse(
            results = listOf(MoviePopularItemResponse(id = 1, title = "Wonka"))
        )

        val expected = response.results.map { it.toDomain() }

        coEvery { api.popularMovies(page, language) } returns Response.success(response)

        //when
        val result = dataSource.getPopularMovies(page)
        result as ResultType.Success

        //then
        assertEquals(expected, result.value)
    }

    @Test
    fun `given params when call getPopularMovies then return error body result`() = runBlocking {
        //given
        val expected = Failure.UnknownError

        coEvery { api.popularMovies(page, language) } returns Response.success(bodyNull)

        //when
        val result = dataSource.getPopularMovies(page)
        result as ResultType.Error

        //then
        assertEquals(expected, result.value)
    }

    @Test
    fun `given params when call getPopularMovies then return error result`() = runBlocking {
        //given
        val expected = Failure.ServerError

        coEvery { api.popularMovies(page, language) } returns Response.error(code404, responseBody)

        //when
        val result = dataSource.getPopularMovies(page)
        result as ResultType.Error

        //then
        assertEquals(expected, result.value)
    }

    @Test
    fun `given params when call getPopularMovies then return failure result`() = runBlocking {
        //given
        val expected = Failure.NetworkError

        //when
        val result = dataSource.getPopularMovies(page)
        result as ResultType.Error

        //then
        assertEquals(expected, result.value)
    }

}
