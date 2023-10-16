package me.yihtseu.jishi.utils.crypto

import java.net.URL
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

fun encryptUrl(baseUrl: String): String {
    val fullUrl = Regex("""https?://(www\.)?[-a-zA-Z0-9@:%._~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_.~#?&/=]*)""")
    val partialUrl = Regex("""(www\.)?[-a-zA-Z0-9@:%._~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_.~#?&/=]*)""")
    val url = when {
        fullUrl.matches(baseUrl) -> URL(baseUrl)
        partialUrl.matches(baseUrl) -> URL("http://" + baseUrl)
        else -> return "about:blank"
    }

    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val keyBytes = "Didida1127Didida".toByteArray(Charsets.UTF_8)
    val ivBytes = "Didida1127Didida".toByteArray(Charsets.UTF_8)
    val secretKey = SecretKeySpec(keyBytes, "AES")
    val ivSpec = IvParameterSpec(ivBytes)
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)

    val protocol = url.protocol.split(':')[0]
    val host = url.host
    val path = url.path + url.query
    val encryptedHost = cipher.doFinal(host.toByteArray(Charsets.UTF_8)).joinToString("") { "%02x".format(it) }
    return "https://vpn.jlu.edu.cn/$protocol/44696469646131313237446964696461$encryptedHost$path"
}

fun encryptUrl(status: Boolean, baseUrl: String): String {
    return if (status) baseUrl else encryptUrl(baseUrl)
}