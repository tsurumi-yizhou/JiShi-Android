package me.yihtseu.jishi.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun AppTheme(
  useDarkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
    val context = LocalContext.current as Activity
    if (useDarkTheme) {
        MaterialTheme(dynamicDarkColorScheme(context), shapes, typography, content)
    } else {
        MaterialTheme(dynamicLightColorScheme(context), shapes, typography, content)
    }
}