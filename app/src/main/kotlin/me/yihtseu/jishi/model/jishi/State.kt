package me.yihtseu.jishi.model.jishi

sealed class State<out T> {
    class Error(val message: String?) : State<Nothing>()
    object Loading : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
}