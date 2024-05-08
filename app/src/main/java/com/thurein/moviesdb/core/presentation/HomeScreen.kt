package com.thurein.moviesdb.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.thurein.moviesdb.presentation.MovieListScreen
import com.thurein.moviesdb.presentation.MovieListViewModel


@Composable
fun HomeScreen(navController: NavHostController) {

    val movieListViewModel = hiltViewModel<MovieListViewModel>()
    val movieState = movieListViewModel.movieListState.collectAsState().value

    Scaffold(
        topBar = {
            SearchBar(modifier = Modifier) // Add margins here

        }
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
@Composable
fun SearchBar(
    modifier: Modifier=Modifier
) {
    val searchTextState = remember { mutableStateOf(TextFieldValue()) }

    OutlinedTextField(
        value = searchTextState.value,
        onValueChange = {
            searchTextState.value = it
            // onSearch(it.text)
        },
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),

        placeholder = {
            Text(
                text = "Search a movie or tv series",
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                // onSearch(searchTextState.value.text)
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },

        )
}

