package com.nadhifhayazee.domain.dto

sealed class ResultState<T> {
    class Initial<T>: ResultState<T>()
    class Loading<T> : ResultState<T>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error<T>(val throwable: Throwable) : ResultState<T>()
}
