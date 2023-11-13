package me.yihtseu.jishi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.jishi.PageState
import javax.inject.Inject

data class NewsDetailState(
    val loading: Boolean = false,
    val message: String? = null,
    val action: String? = null,
    val title: String = "",
    val content: String = ""
)

@HiltViewModel
class NewsDetailViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(NewsDetailState())
    val state = _state.asStateFlow()

    fun init() = viewModelScope.launch {
        try {
            _state.update {
                it.copy(title = PageState.title!!, content = PageState.content!!)
            }
        } catch (e: Exception) {
            _state.update { it.copy(message = e.localizedMessage) }
        }
    }
}