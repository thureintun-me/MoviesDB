package com.thurein.moviesdb.data.repository

import com.thurein.moviesdb.data.local.movie.MovieDatabase
import com.thurein.moviesdb.data.mapper.toMovie
import com.thurein.moviesdb.data.mapper.toMovieEntity
import com.thurein.moviesdb.data.remote.MovieApi
import com.thurein.moviesdb.domain.model.Movie
import com.thurein.moviesdb.domain.repository.MovieListRepository
import com.thurein.moviesdb.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
class MovieListRepositoryImpl
    @Inject constructor(
        private val movieApi: MovieApi,
        private val movieDatabase: MovieDatabase

    )
    : MovieListRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        page: Int
    ): Flow<Resource<List<Movie>>> {

        return flow {
            emit(Resource.Loading(isLoading = true))
            val localMovieList = movieDatabase.movieDao.getMovieList();

            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalMovie){
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity -> movieEntity.toMovie() }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val movieListByApi = try {
                movieApi.getMoviesList(page);

            }catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }catch (e:HttpException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            val movieEntities = movieListByApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity()
                }
            }
            movieDatabase.movieDao.upsertMovieList(movieEntities)
            emit(Resource.Success(
                movieEntities.map { it.toMovie() }
            ))
            emit(Resource.Loading(false))
        }
    }



    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.getMovieById(id)

            if(movieEntity !== null){
                emit(Resource.Success(
                    movieEntity.toMovie()
                ))
                emit(Resource.Loading(true))
                return@flow
            }

            emit(Resource.Error(message = "Error no such movie"))
        }
    }

}