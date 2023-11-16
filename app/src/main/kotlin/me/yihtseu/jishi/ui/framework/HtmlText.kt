package me.yihtseu.jishi.ui.framework

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ListAlt
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import me.yihtseu.jishi.model.jishi.PageState
import me.yihtseu.jishi.ui.theme.typography
import org.jsoup.nodes.Element

@Composable
fun Html(element: Element, modifier: Modifier = Modifier) {
    when (element.tagName()) {
        "h1" -> Text(text = element.text(), style = typography.titleLarge, modifier = modifier)
        "h2" -> Text(text = element.text(), style = typography.titleMedium, modifier = modifier)
        "h3" -> Text(text = element.text(), style = typography.titleSmall, modifier = modifier)
        "i" -> Text(text = element.text(), style = typography.bodyMedium, modifier = modifier)
        "p" -> {
            Text(text = element.text(), style = typography.bodyMedium, modifier = modifier)
            element.children().forEach {
                Html(it, modifier)
            }
        }
        "li" -> Text(text = element.text(), style = typography.labelSmall, modifier = modifier.clickable { })
        "img" -> {
            val url =
                if (element.attributes()["src"].startsWith("http")) {
                    element.attributes()["src"]
                } else {
                    "https://" + Uri.parse(PageState.url).host + element.attributes()["src"]
                }
            Log.d("image", url)
            AsyncImage(url, null, modifier)
        }

        "ol" -> element.children().forEachIndexed { index, element ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "${index + 1}. ", style = typography.bodySmall, modifier = modifier)
                Html(element, modifier)
            }
        }

        "ul" -> element.children().forEach { element ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(Icons.Outlined.ListAlt, null)
                Html(element, modifier)
            }
        }

        "div" -> element.children().forEach { Html(it, modifier) }
        "figure" -> element.children().forEach { Html(it, modifier) }
        "figcaption" -> element.children().forEach { Html(it, modifier) }
        "blockquote" -> ElevatedCard(modifier = modifier) {
            element.children().forEach { Html(it, modifier) }
        }

        else -> {}
    }
}