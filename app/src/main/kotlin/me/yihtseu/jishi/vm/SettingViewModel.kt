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
import me.yihtseu.jishi.R
import me.yihtseu.jishi.repo.GithubRepository
import javax.inject.Inject

data class SettingState(
    val message: String? = null
)

@HiltViewModel
class SettingViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    fun checkUpdate() = viewModelScope.launch {
        try {
            val latest = githubRepository.fetchLatestRelease("nightly")
            _state.update { it.copy(message = latest.name) }
        } catch (e: Exception) {
            _state.update { it.copy(message = context.getString(R.string.update_error)) }
        }
    }
}