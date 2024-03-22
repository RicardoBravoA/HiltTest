package com.rba.hilttest.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rba.hilttest.data.util.Failure
import com.rba.hilttest.data.util.ResultType
import com.rba.hilttest.domain.model.MovieModel
import com.rba.hilttest.domain.usecase.MoviePopularUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val useCase: MoviePopularUseCase,
) : ViewModel() {

    private val _popularMovies = MutableLiveData<List<MovieModel>?>()
    val popularMovies: LiveData<List<MovieModel>?>
        get() = _popularMovies

    private val _error = MutableLiveData<Failure?>()
    val error: LiveData<Failure?>
        get() = _error

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            when (val result = useCase.invoke(1)) {
                is ResultType.Success -> {
                    _popularMovies.value = result.value
                }

                is ResultType.Error -> {
                    _error.value = result.value
                }
            }
        }
    }

}
