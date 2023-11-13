package me.yihtseu.jishi.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.jishi.DataStore
import me.yihtseu.jishi.repo.CasRepository
import javax.inject.Inject

data class LoginState(
    val loading: Boolean = true,
    val success: Boolean = false,
    val message: String? = null,
    val username: String? = null,
    val password: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val casRepository: CasRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun preLogin() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val username = DataStore.getString("jlu_username")?.first()
            val password = DataStore.getString("jlu_password")?.first()
            _state.update {
                it.copy(loading = false, username = username, password = password)
            }
            if (username != null && password != null) {
                login(username, password)
            }
        } catch (e: Exception) {
            _state.update { it.copy(loading = false, message = e.localizedMessage) }
        }
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val result = casRepository.checkLogin(username, password)
            Log.d("login", result.toString())
            if (result) {
                DataStore.setString("jlu_username", username)
                DataStore.setString("jlu_password", password)
                _state.update {
                    it.copy(loading = true, success = true)
                }
            } else {
                _state.update {
                    it.copy(loading = false, success = false)
                }
            }
        } catch (e: Exception) {
            _state.update { it.copy(loading = false, success = false, message = e.localizedMessage) }
        }
    }
}