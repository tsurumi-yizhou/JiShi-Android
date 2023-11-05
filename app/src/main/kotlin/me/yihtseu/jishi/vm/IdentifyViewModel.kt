package me.yihtseu.jishi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.repo.HallRepository
import javax.inject.Inject

data class IdentifyState(
    val loading: Boolean = true,
    val image: ByteArray? = null,
    val message: String? = null
)

@HiltViewModel
class IdentifyViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow(IdentifyState())
    val state = _state.asStateFlow()

    fun load() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            _state.update { it.copy(loading = false, image = HallRepository.qrcode()) }
        } catch (e: Exception) {
            _state.update { it.copy(message = e.localizedMessage) }
        }
    }
}