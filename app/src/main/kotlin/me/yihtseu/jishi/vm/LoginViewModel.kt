package me.yihtseu.jishi.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.jishi.DataStore
import me.yihtseu.jishi.model.jishi.Result
import me.yihtseu.jishi.repo.CasRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
) : ViewModel() {

    private val _state = MutableStateFlow<Result<Boolean>>(Result.Loading)
    val state = _state.asStateFlow()

    fun preLogin(context: Context) = viewModelScope.launch {
        try {
            val username = DataStore.getString("jlu_username").first()
            val password = DataStore.getString("jlu_password").first()
            if (username.isNullOrBlank() || password.isNullOrBlank())
                _state.update { Result.Error(null) }
            else
                _state.update { Result.Success(CasRepository.checkLogin(username, password)) }
        } catch (e: Exception) {
            _state.update { Result.Error(null) }
        }
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        _state.update { Result.Loading }
        try {
            DataStore.setString("jlu_username", username)
            DataStore.setString("jlu_password", password)
            _state.update { Result.Success(CasRepository.checkLogin(username, password)) }
        } catch (e: Exception) {
            _state.update { Result.Error(e.localizedMessage.orEmpty()) }
        }
    }
}