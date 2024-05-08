package com.thurein.moviesdb.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.thurein.moviesdb.presentation.MovieListScreen
import com.thurein.moviesdb.presentation.MovieListViewModel


@Composable
fun HomeScreen(navController: NavHostController) {

    val movieListViewModel = hiltViewModel<MovieListViewModel>()
    val movieState = movieListViewModel.movieListState.collectAsState().value

    Scaffold(
        
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            MovieListScreen(
                movieListState = movieState,
                navController = navController,
                onEvent = movieListViewModel::onEvent
            )

        }
    }
}

