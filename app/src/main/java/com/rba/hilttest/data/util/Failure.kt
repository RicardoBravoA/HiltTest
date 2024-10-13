package com.rba.hilttest.data.util

sealed class Failure(val message: String) {
    data object NetworkError : Failure("Network Error")
    data object ServerError : Failure("Server Error")
    data object UnknownError : Failure("Unknown Error")
}