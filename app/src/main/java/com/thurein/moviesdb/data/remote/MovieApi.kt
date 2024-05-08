package com.thurein.moviesdb.data.remote

import com.thurein.moviesdb.data.remote.respond.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/movies")
    suspend fun getMoviesList(
        @Query("page") page:Int,
        @Query("api_key") apiKey:String= API_KEY,
    ):MovieListDto

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "9f04c49e56282d595c3ac1fa31ea742d"
    }
}