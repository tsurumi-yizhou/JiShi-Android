package me.yihtseu.jishi.error

import me.yihtseu.jishi.R

class DataError : BaseError, Throwable() {
    override val messageRes: Int
        get() = R.string.data_error

}