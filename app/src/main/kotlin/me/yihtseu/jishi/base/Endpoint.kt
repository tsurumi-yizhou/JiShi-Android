package me.yihtseu.jishi.base

data class Endpoint(
    val url: String,
    val vpnUrl: String = url,
    val headers: Map<String, String> = emptyMap()
)
