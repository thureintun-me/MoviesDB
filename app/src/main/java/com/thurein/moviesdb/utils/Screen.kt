package com.thurein.moviesdb.utils

sealed class Screen(val rout: String) {
    object Home : Screen("main")
    object Details : Screen("details")
}