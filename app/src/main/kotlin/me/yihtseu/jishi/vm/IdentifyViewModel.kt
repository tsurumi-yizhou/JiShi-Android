package me.yihtseu.jishi.vm

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.jishi.State
import me.yihtseu.jishi.repo.HallRepository
import javax.inject.Inject

@HiltViewModel
class IdentifyViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow<State<ByteArray>>(State.Loading)
    val state = _state.asStateFlow()

    fun load(hostState: SnackbarHostState) = viewModelScope.launch {
        _state.update { State.Loading }
        try {
            _state.update { State.Success(HallRepository.qrcode()) }
        } catch (e: Exception) {
            hostState.showSnackbar(e.localizedMessage.orEmpty())
            _state.update { State.Error(e.localizedMessage) }
        }
    }
}