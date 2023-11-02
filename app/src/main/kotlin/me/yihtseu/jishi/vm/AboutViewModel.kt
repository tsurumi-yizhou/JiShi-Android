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

@HiltViewModel
class AboutViewModel @Inject constructor(
) : ViewModel() {
    private val _contributors = MutableStateFlow<List<Contributor>>(emptyList())
    val contributors = _contributors.asStateFlow()

    fun init() = viewModelScope.launch {
        try {
            _contributors.update { GithubRepository.fetchContributors() }
        } catch (e: Exception) {
            _contributors.update { emptyList() }
        }
    }

}