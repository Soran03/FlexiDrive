package com.example.carrentalapp.DataModels

sealed class ResultModel<out T> {
    data class Success<out T>(val data: T) : ResultModel<T>()
    data class Error(val exception: Exception) : ResultModel<Nothing>()
}
