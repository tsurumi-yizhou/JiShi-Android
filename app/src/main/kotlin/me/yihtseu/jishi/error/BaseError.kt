package me.yihtseu.jishi.error

import androidx.annotation.StringRes

interface BaseError {
    @get:StringRes
    val messageRes: Int
}