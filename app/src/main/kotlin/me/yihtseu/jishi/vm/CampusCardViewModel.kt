package me.yihtseu.jishi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.base.DataStore
import javax.inject.Inject

data class CampusCardState(
    val loading: Boolean = true,
    val message: String? = null,
    val login: Boolean = false,
    val username: String? = null,
    val password: String? = null
)

@HiltViewModel
class CampusCardViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(CampusCardState())
    val state = _state.asStateFlow()

    fun init() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val username = DataStore.getString(usernameKey)?.first()
            val password = DataStore.getString(passwordKey)?.first()
            if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
                _state.update { it.copy(loading = false, username = username, password = password, login = false) }
            } else {
                login(username, password)
            }
        } catch (e: Exception) {
            _state.update { it.copy(loading = false, message = e.localizedMessage) }
        }
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            DataStore.setString(usernameKey, username)
            DataStore.setString(passwordKey, password)
            _state.update { it.copy(loading = false, login = true, username = username, password = password) }
        } catch (e: Exception) {
            _state.update { it.copy(loading = false, message = e.localizedMessage) }
        }
    }

    fun exit() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            DataStore.setString(usernameKey, "")
            DataStore.setString(passwordKey, "")
            _state.update { it.copy(loading = false, login = false) }
        } catch (e: Exception) {
            _state.update { it.copy(loading = false, message = e.localizedMessage) }
        }
    }

    companion object {
        val usernameKey = "jlu_campus_card_username"
        val passwordKey = "jlu_campus_card_password"
    }
}