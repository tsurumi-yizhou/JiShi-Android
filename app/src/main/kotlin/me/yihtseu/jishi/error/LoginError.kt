package me.yihtseu.jishi.error

import me.yihtseu.jishi.R

class LoginError: BaseError, Throwable() {
    override val messageRes: Int
        get() = R.string.login_error

}