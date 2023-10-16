package me.yihtseu.jishi.error

import me.yihtseu.jishi.R

class NetworkError: BaseError, Throwable() {
    override val messageRes: Int
        get() = R.string.network_error

}