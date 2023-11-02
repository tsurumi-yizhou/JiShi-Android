package me.yihtseu.jishi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.campus.space.StudentInfo
import me.yihtseu.jishi.model.jishi.DataStore
import me.yihtseu.jishi.model.jishi.State
import me.yihtseu.jishi.repo.CasRepository
import javax.inject.Inject

data class CampusEmailState(
    val profile: StudentInfo,
    val avatar: ByteArray
)

@HiltViewModel
class CampusEmailViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<State<CampusEmailState>>(State.Loading)
    val state = _state.asStateFlow()

    fun init() = viewModelScope.launch {
        _state.update { State.Loading }
        try {
            _state.update {
                State.Success(
                    CampusEmailState(
                        profile = CasRepository.fetchProfile(),
                        avatar = CasRepository.fetchPicture()
                    )
                )
            }
        } catch (e: Exception) {
            _state.update {
                State.Error(e.localizedMessage)
            }
        }
    }

    fun exit() = viewModelScope.launch {
        DataStore.setString("jlu_username", "")
        DataStore.setString("jlu_password", "")
    }
}