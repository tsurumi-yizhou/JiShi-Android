package me.yihtseu.jishi.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.jishi.DataStore
import me.yihtseu.jishi.model.jishi.NfcState
import javax.inject.Inject

data class NfcCardState(
    val message: String? = null,
    val loading: Boolean = true,
    val cardId: String = ""
)

@HiltViewModel
class NfcCardViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(NfcCardState())
    val state = _state.asStateFlow()

    fun load() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            DataStore.getString("nfc_card_id")!!.collect { id ->
                if (id.isNullOrBlank()) {
                    NfcState.enable()
                    _state.update { it.copy(loading = false) }
                } else {
                    NfcState.disable()
                    _state.update { it.copy(loading = false, cardId = id) }
                }
            }
        } catch (e: Exception) {
            _state.update { it.copy(loading = false, message = e.localizedMessage) }
        }
    }

    fun delete() = viewModelScope.launch {
        DataStore.setString("nfc_card_id", "")
        DataStore.setString("nfc_card_data", "")
        _state.update { it.copy(cardId = "") }
    }
}