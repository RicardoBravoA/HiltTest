package com.rba.hilttest.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    private val page = 1

    fun getPopularMovies() {
        viewModelScope.launch {
            _viewState.value = ViewState.ShowLoading

            val result = useCase(page)

            if (result is ResultType.Success) {
                _viewState.value = ViewState.Data(result.value)
            }

            if (result is ResultType.Error) {
                _viewState.value = ViewState.Error(result.value.message)
            }

            _viewState.value = ViewState.HideLoading
        }
    }

    sealed class ViewState {
        data object ShowLoading : ViewState()
        data object HideLoading : ViewState()
        data class Error(val message: String) : ViewState()
        data class Data(val data: List<MovieModel>) : ViewState()
    }

}
