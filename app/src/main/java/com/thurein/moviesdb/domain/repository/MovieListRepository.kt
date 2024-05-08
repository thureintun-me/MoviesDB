package com.thurein.moviesdb.domain.repository

import com.thurein.moviesdb.domain.model.Movie
import com.thurein.moviesdb.utils.Resource
import kotlinx.coroutines.flow.Flow


interface MovieListRepository {

    suspend fun getMovieList(
        forceFetchFromRemote:Boolean,
        page:Int
    ):Flow<Resource<List<Movie>>>

    suspend fun getMovie(id:Int):Flow<Resource<Movie>>
}