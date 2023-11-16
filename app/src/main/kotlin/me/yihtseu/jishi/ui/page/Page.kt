package me.yihtseu.jishi.ui.page

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import me.yihtseu.jishi.R

data class Page(@StringRes val name: Int, val show: @Composable (NavHostController) -> Unit)

val pages = mapOf(
    "login" to Page(R.string.login) { LoginScreen(it) },
    "home" to Page(R.string.home) { HomeScreen(it) },
    "news" to Page(R.string.news) { NewsScreen(it) },
    "setting" to Page(R.string.setting) { SettingScreen(it) },
    "qrcode" to Page(R.string.qrcode) { IdentifyScreen(it) },
    "calendar" to Page(R.string.calendar) { CalendarScreen(it) },
    "library" to Page(R.string.library) { LibraryScreen(it) },
    "classrooms" to Page(R.string.classroom) { ClassroomScreen(it) },
    "score" to Page(R.string.score) { ScoreScreen(it) },
    "campus_email" to Page(R.string.campus_account) { CampusEmailScreen(it) },
    "campus_card" to Page(R.string.campus_card_account) { CampusCardScreen(it) },
    "about" to Page(R.string.about) { AboutScreen(it) },
    "subscription" to Page(R.string.theme_subscription) { SubscriptionScreen(it) },
    "license" to Page(R.string.opensource_license) { LicenseScreen(it) },
    "news_detail" to Page(R.string.news_detail) { NewsDetailScreen(it) }
)