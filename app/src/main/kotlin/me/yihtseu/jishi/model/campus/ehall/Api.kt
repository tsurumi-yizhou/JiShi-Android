package me.yihtseu.jishi.model.campus.ehall

object Api {
    val headers = mapOf(
        "Host" to "ehall.jlu.edu.cn",
        "User-Agent" to "Mozilla/5.0 (Linux; Android 11; Pixel 3a) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Mobile Safari/537.36"
    )
    val identifyCodeUrl = "https://ehall.jlu.edu.cn/jlu_identitycode/IdentityCode_phone"
}