package me.yihtseu.jishi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.github.Contributor
import me.yihtseu.jishi.repo.GithubRepository
import javax.inject.Inject

data class AboutState(
    val contributors: List<Contributor> = emptyList()
)

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AboutState())
    val state = _state.asStateFlow()

    fun init() = viewModelScope.launch {
        try {
            _state.update { it.copy(contributors = githubRepository.fetchContributors()) }
        } catch (e: Exception) {
            _state.update { it.copy(contributors = emptyList()) }
        }
    }

}