package me.yihtseu.jishi.vm

import androidx.lifecycle.ViewModel
import com.prof18.rssparser.RssParser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(): ViewModel() {
    private val parser = RssParser()
}