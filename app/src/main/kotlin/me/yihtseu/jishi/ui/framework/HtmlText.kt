package me.yihtseu.jishi.ui.framework

import android.net.Uri
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import me.yihtseu.jishi.model.jishi.PageState
import me.yihtseu.jishi.ui.theme.typography
import org.jsoup.nodes.Element

@Composable
fun HtmlText(
    tag: String, attributes: Map<String, String>, text: String,
    modifier: Modifier = Modifier
) {
    when (tag) {
        "h1" -> Text(text = text, style = typography.titleLarge, modifier = modifier)
        "h2" -> Text(text = text, style = typography.titleMedium, modifier = modifier)
        "h3" -> Text(text = text, style = typography.titleSmall, modifier = modifier)
        "p" -> Text(text = text, style = typography.bodyMedium, modifier = modifier)
        "li" -> Text(text = text, style = typography.labelSmall, modifier = modifier)
        "img" -> {
            if (attributes["src"].isNullOrBlank()) return
            if (attributes["src"]!!.startsWith("http")) {
                AsyncImage(attributes["src"], null, modifier = modifier)
            } else {
                AsyncImage("https://" + Uri.parse(PageState.url).host + attributes["src"], null, modifier = modifier)
            }
        }

        "figcaption" -> Text(text = text, style = typography.labelSmall, modifier = modifier)
        else -> Text("<$tag ${attributes}> $text </$tag>", modifier = modifier)
    }
}

@Composable
fun Html(element: Element, modifier: Modifier = Modifier) {
    HtmlText(element.tagName(), element.attributes().dataset(), element.text(), modifier)
    if (element.children().isNotEmpty()) {
        element.children().forEach { Html(it, modifier) }
    }
}