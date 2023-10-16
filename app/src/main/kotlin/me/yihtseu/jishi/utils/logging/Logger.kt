package me.yihtseu.jishi.utils.logging

interface Logger {
    fun logEvent(name: String, arguments: Map<String, String>)
}