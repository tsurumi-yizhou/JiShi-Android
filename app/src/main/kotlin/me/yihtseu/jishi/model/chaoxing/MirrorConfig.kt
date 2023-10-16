package me.yihtseu.jishi.model.chaoxing


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MirrorConfig(
    val result: Int,
    val data: Data
) {
    @Serializable
    data class Data(
        val isMirrorDeploy: Boolean,
        val domainMap: DomainMap
    ) {
        @Serializable
        data class DomainMap(
            @SerialName("GroupStatisticsDomain")
            val groupStatisticsDomain: String,
            @SerialName("mooc1_2Domain")
            val mooc12Domain: String,
            val shareWh1DomainHttps: String,
            val jwFyDomain: String,
            val swfilterDomain: String,
            val homeDomain: String,
            val photoDomainHttps: String,
            val mobileWxDomain: String,
            val structureDomain: String,
            val appsDomainHttps: String,
            val officeDomain: String,
            val smsDomain: String,
            val csDomainHttps: String,
            val specialDomainHttps: String,
            val stat2DomainHttps: String,
            @SerialName("GroupWebDomainHttps")
            val groupWebDomainHttps: String,
            val v1Domain: String,
            val passport2Domain: String,
            @SerialName("NoteDomain")
            val noteDomain: String,
            val iMoocDomain: String,
            @SerialName("NoticeDomain")
            val noticeDomain: String,
            @SerialName("UserDomainHttps")
            val userDomainHttps: String,
            val liveSuperlibDomainHttps: String,
            val guanliDomain: String,
            val shareWhDomain: String,
            val d0DomainHttps: String,
            val shareWhDomainHttps: String,
            val uc1DomainHttps: String,
            val panDomain: String,
            val d0Domain: String,
            val transydDomainHttps: String,
            val xDomainHttps: String,
            val mooc1ApiDomainHttps: String,
            val photoDomain: String,
            val specieDomainHttps: String,
            @SerialName("UserDomain")
            val userDomain: String,
            val mooc2AnsDomain: String,
            val passport2DomainHttps: String,
            val studyApiDomain: String,
            val previewDomainHttps: String,
            val wordDomainHttps: String,
            val lcydDomainHttps: String,
            val homeWhDomainHttps: String,
            val structureDomainHttps: String,
            val messageDomainHttps: String,
            val mobileLearnFyDomain: String,
            @SerialName("SpecialPackDomainHttps")
            val specialPackDomainHttps: String,
            val pcDomain: String,
            val wechatDomain: String,
            val appsWhDomainHttps: String,
            @SerialName("fystat1_1FyDomain")
            val fystat11FyDomain: String,
            val groupweb2DomainHttps: String,
            @SerialName("GroupWebDomain")
            val groupWebDomain: String,
            val passport2ApiDomainHttps: String,
            val feDomain: String,
            val learnDomainHttps: String,
            val csDomain: String,
            val shortUrlDomain: String,
            val commendDomainHttps: String,
            val exportDomain: String,
            val kbDomainHttps: String,
            val mobilelearnDomain: String,
            val manageLearnDomain: String,
            val contactsDomainHttps: String,
            val cvDomain: String,
            val fyLbDomainHttps: String,
            val mobilelearnDomainHttps: String,
            val commDomain: String,
            val imApi1Domain: String,
            val moocDomain: String,
            val mooc1ApiDomain: String,
            val compDomain: String,
            val ucDomain: String,
            val astatsFyDomain: String,
            val avatarDomain: String,
            val ktDomainHttps: String,
            @SerialName("NoteDomainHttps")
            val noteDomainHttps: String,
            val csAPIDomain: String,
            val uc1Domain: String,
            val specialRhykDomainHttps: String,
            val vspaceDomain: String,
            val moocDomainHttps: String,
            val learnDomain: String,
            @SerialName("GroupDomain")
            val groupDomain: String,
            val panDomainHttps: String,
            val resourceDomain: String,
            val menhuDomain: String,
            val mDomain: String,
            val specialDomain: String,
            @SerialName("mooc1_3Domain")
            val mooc13Domain: String,
            @SerialName("CourseDomain")
            val courseDomain: String,
            val appsDomain: String,
            val aiDomainHttps: String,
            @SerialName("GroupStatisticsDomainHttps")
            val groupStatisticsDomainHttps: String,
            @SerialName("GroupDomainHttps")
            val groupDomainHttps: String,
            val messageDomain: String,
            val contestDomain: String,
            val cooperateDomainHttps: String,
            val jwDomain: String,
            @SerialName("NoticeDomainHttps")
            val noticeDomainHttps: String,
            val exportDomainHttps: String,
            val ypDomainHttps: String,
            val zhiboDomainHttps: String,
            val moaDomain: String,
            val wpsDomainHttps: String,
            val mobileFyDomain: String,
            val mooc1Domain: String,
            @SerialName("SpecialPackDomain")
            val specialPackDomain: String,
            val mooc1DomainHttps: String
        )
    }
}