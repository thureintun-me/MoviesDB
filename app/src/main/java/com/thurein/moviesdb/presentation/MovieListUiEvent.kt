package com.thurein.moviesdb.presentation

sealed interface MovieListUiEvent {
    data class Paginate(val category: String? = null) : MovieListUiEvent


}