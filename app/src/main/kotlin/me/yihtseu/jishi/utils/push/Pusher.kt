package me.yihtseu.jishi.utils.push

interface Pusher {
    fun subscribe(topic: String)
}