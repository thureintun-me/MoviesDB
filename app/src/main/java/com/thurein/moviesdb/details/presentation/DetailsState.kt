package com.thurein.moviesdb.details.presentation

import com.thurein.moviesdb.domain.model.Movie

data class DetailsState (

    val isLoading:Boolean = false,
    val movie: Movie? = null
)