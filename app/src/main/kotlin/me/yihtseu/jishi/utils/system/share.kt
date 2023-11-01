package me.yihtseu.jishi.utils.system

import android.content.Context
import android.content.Intent

fun share(context: Context, text: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, null))
}