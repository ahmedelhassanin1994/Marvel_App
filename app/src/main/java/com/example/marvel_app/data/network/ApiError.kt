package com.example.marvel_app.data.network

data class ApiError(
    val isNoInternet: Boolean = false,
    val httpCode: Int = -1,
    val throwable: Throwable
) : Exception(throwable)

