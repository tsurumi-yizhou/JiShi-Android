package me.yihtseu.jishi.model.campus.edu

object Api {
    val headers = mapOf(
        "Host" to "iedu.jlu.edu.cn",
        "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML to like Gecko) Chrome/118.0.0.0 Safari/537.36",
        "Accept" to "application/json to text/javascript to */*; q=0.01",
        "Accept-Language" to "zh-CN tozh;q=0.8 tozh-TW;q=0.7 tozh-HK;q=0.5 toen-US;q=0.3 toen;q=0.2",
        "Accept-Encoding" to "gzip to deflate to br",
        "Content-Type" to "application/x-www-form-urlencoded; charset=UTF-8",
        "X-Requested-With" to "XMLHttpRequest",
        "Origin" to "https://iedu.jlu.edu.cn",
        "Connection" to "keep-alive",
        "Referer" to "https://iedu.jlu.edu.cn/jwapp/sys/kxjas/*default/index.do?THEME=green&EMAP_LANG=zh",
    )
    fun appConfigUrl(service: String) = "https://iedu.jlu.edu.cn/jwapp/sys/funauthapp/api/getAppConfig/$service-4770397878132218.do"
    fun roleUrl(id: String) = "https://iedu.jlu.edu.cn/jwapp/sys/jwpubapp/pub/setJwCommonAppRole.do?ROLEID=$id"
    val termUrl = "https://iedu.jlu.edu.cn/jwapp/sys/wdkb/modules/jshkcb/cxjcs.do"
    fun lessonUrl(year: String, term: String, week: String) = "https://iedu.jlu.edu.cn/jwapp/sys/wdkb/modules/xskcb/cxxszhxqkb.do?XNXQDM=$year-$term&SKZC=$week"
    val indexUrl = "https://iedu.jlu.edu.cn/jwapp/sys/emaphome/portal/index.do"
    val cxjwggbbdqxUrl = "https://iedu.jlu.edu.cn/jwapp/sys/jwpubapp/modules/bb/cxjwggbbdqx.do"
    val kxjasUrl = "https://iedu.jlu.edu.cn/jwapp/sys/emappagelog/config/kxjas.do"
    val kxjscxUrl = "https://iedu.jlu.edu.cn/jwapp/sys/kxjas/modules/kxjscx.do?*json=1"
    val cxkxjsUrl = "https://iedu.jlu.edu.cn/jwapp/sys/kxjas/modules/kxjscx/cxkxjs.do"
}