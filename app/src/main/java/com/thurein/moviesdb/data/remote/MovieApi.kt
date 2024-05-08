package com.thurein.moviesdb.data.remote

import com.thurein.moviesdb.data.remote.respond.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getMoviesList(
        @Query("page") page:Int,
        @Query("api_key") apiKey:String= API_KEY,
    ):MovieListDto

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "631724d38a00168efda1385b0b5efd9c"
    }
}