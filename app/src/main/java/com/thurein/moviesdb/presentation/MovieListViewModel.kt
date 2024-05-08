package com.thurein.moviesdb.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thurein.moviesdb.domain.repository.MovieListRepository
import com.thurein.moviesdb.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {

    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        getMovieList(false);
    }

    fun onEvent(event: MovieListUiEvent) {
        when (event) {

            is MovieListUiEvent.Paginate -> {
                getMovieList(true)
            }
        }
    }



    private fun getMovieList(forceFetchFromRemote:Boolean){
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            movieListRepository.getMovieList(forceFetchFromRemote,movieListState.value.movieListPage).collectLatest {
                result ->
                when(result){
                    is Resource.Error ->{
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let {
                            movieList -> _movieListState.update {
                                it.copy(
                                    movieList = movieListState.value.movieList
                                            + movieList.shuffled(),
                                    movieListPage = movieListState.value.movieListPage + 1
                                )
                        }
                        }
                    }
                    is Resource.Loading ->{
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }
}