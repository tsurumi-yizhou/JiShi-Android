package me.yihtseu.jishi.ui

import androidx.annotation.StringRes
import me.yihtseu.jishi.R

sealed class Navigation(
    @StringRes val id: Int
) {
    object MainScreen: Navigation(R.string.main)

    object LoginScreen: Navigation(R.string.login)

    object HomeScreen: Navigation(R.string.home)

    object NewsScreen: Navigation(R.string.news)

    object SettingScreen: Navigation(R.string.setting)

    object IdentifyScreen: Navigation(R.string.qrcode)

    object CalendarScreen: Navigation(R.string.calendar)

    object LibraryScreen: Navigation(R.string.library)

    object ClassroomScreen: Navigation(R.string.classroom)

    object ScoreScreen: Navigation(R.string.score)

    object CampusEmailScreen : Navigation(R.string.campus_account)

    object CampusCardScreen : Navigation(R.string.campus_card_account)

    object AboutScreen : Navigation(R.string.about)

    object SubscriptionScreen : Navigation(R.string.theme_subscription)

    object LicenseScreen : Navigation(R.string.opensource_license)
}