package com.thurein.moviesdb.presentation

import com.thurein.moviesdb.domain.model.Movie

data class MovieListState(
    val isLoading:Boolean = false,
    val movieListPage : Int = 1,
    val movieList : List<Movie> = emptyList()
)