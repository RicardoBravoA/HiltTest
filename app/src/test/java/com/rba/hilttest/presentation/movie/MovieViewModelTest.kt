package com.rba.hilttest.presentation.movie

import com.rba.hilttest.base.BaseViewModelTest
import com.rba.hilttest.data.util.Failure
import com.rba.hilttest.data.util.ResultType
import com.rba.hilttest.domain.model.MovieModel
import com.rba.hilttest.domain.usecase.MoviePopularUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest : BaseViewModelTest() {

    @InjectMockKs
    private lateinit var viewModel: MovieViewModel

    @MockK
    private lateinit var useCase: MoviePopularUseCase
    private val page = 1

    private val viewState: MutableList<MovieViewModel.ViewState> = mutableListOf()

    override fun setUp() {
        super.setUp()

        viewModel.viewState.observeForever {
            viewState.add(it)
        }
    }

    override fun tearDown() {
        super.tearDown()
        viewState.clear()
    }

    @Test
    fun `given useCase when call getPopularMovies then return success result`() = runTest {
        //given
        val expected = mockk<List<MovieModel>>()
        coEvery { useCase(page) } returns ResultType.Success(expected)

        //when
        viewModel.getPopularMovies()

        //then
        assertEquals(MovieViewModel.ViewState.ShowLoading, viewState[0])
        val data = viewState[1] as MovieViewModel.ViewState.Data
        assertEquals(expected, data.data)
        assertEquals(MovieViewModel.ViewState.HideLoading, viewState[2])
    }

    @Test
    fun `given useCase when call getPopularMovies then return error result`() = runTest {
        //given
        val response = Failure.NetworkError
        val expected = response.message
        coEvery { useCase(page) } returns ResultType.Error(response)

        //when
        viewModel.getPopularMovies()

        //then
        assertEquals(MovieViewModel.ViewState.ShowLoading, viewState[0])
        val error = viewState[1] as MovieViewModel.ViewState.Error
        assertEquals(expected, error.message)
        assertEquals(MovieViewModel.ViewState.HideLoading, viewState[2])
    }

}
