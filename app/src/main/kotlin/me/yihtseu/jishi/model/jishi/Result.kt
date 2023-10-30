package me.yihtseu.jishi.model.jishi

sealed class Result<out T> {
    class Error(val message: String?) : Result<Nothing>()
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
}