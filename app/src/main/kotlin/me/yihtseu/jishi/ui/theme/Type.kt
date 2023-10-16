package me.yihtseu.jishi.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/*
val LXGWMono = FontFamily(
    Font(R.font.lxgwmono, FontWeight.Normal),
    Font(R.font.lxgwmono_bold, FontWeight.Bold),
    Font(R.font.lxgwmono_light, FontWeight.Light)
)

val sourceSans = FontFamily(
    Font(R.font.source_sans_pro_light, FontWeight.Light),
    Font(R.font.source_sans_pro, FontWeight.Normal),
    Font(R.font.source_sans_pro_semibold, FontWeight.SemiBold),
    Font(R.font.source_sans_pro_bold, FontWeight.Bold),
    Font(R.font.source_sans_pro_black, FontWeight.Black)
)

val sourceSerif = FontFamily(
    Font(R.font.source_serif_pro, FontWeight.Normal),
    Font(R.font.source_serif_pro_semibold, FontWeight.SemiBold),
    Font(R.font.source_serif_pro_bold, FontWeight.Bold)
)*/

val typography = Typography(
    titleLarge = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
    titleMedium = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
    titleSmall = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
    labelLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
    labelMedium = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
    labelSmall = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
    bodyLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal),
    bodyMedium = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal),
    bodySmall = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
)