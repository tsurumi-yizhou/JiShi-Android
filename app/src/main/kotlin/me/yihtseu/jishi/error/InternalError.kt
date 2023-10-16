package me.yihtseu.jishi.error

import me.yihtseu.jishi.R

class InternalError : BaseError, Throwable() {
    override val messageRes: Int
        get() = R.string.internal_error

}