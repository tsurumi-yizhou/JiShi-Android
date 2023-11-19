package me.yihtseu.jishi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.base.DataStore
import me.yihtseu.jishi.model.campus.space.StudentInfo
import me.yihtseu.jishi.repo.CasRepository
import javax.inject.Inject

data class CampusEmailState(
    val message: String? = null,
    val loading: Boolean = true,
    val profile: StudentInfo? = null,
    val avatar: ByteArray? = null
)

@HiltViewModel
class CampusEmailViewModel @Inject constructor(
    private val casRepository: CasRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CampusEmailState())
    val state = _state.asStateFlow()

    fun init() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            _state.update {
                it.copy(
                    loading = false,
                    profile = casRepository.fetchProfile(),
                    avatar = casRepository.fetchPicture()
                )
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    loading = false,
                    message = e.localizedMessage
                )
            }
        }
    }

    fun exit() = viewModelScope.launch {
        DataStore.setString("jlu_username", "")
        DataStore.setString("jlu_password", "")
    }
}