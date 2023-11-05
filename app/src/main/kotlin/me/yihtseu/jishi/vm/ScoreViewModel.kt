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
import me.yihtseu.jishi.model.campus.edu.ScoreResult
import me.yihtseu.jishi.repo.EduRepository
import javax.inject.Inject

data class ScoreState(
    val loading: Boolean = true,
    val message: String? = null,
    val subjects: List<ScoreResult.Datas.Xscjcx.Row> = emptyList(),
    val score: Double = 0.0,
    val selected: List<String> = emptyList(),
    val weight: Boolean = true
)

@HiltViewModel
class ScoreViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(ScoreState())
    val state = _state.asStateFlow()

    fun load() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val result = EduRepository.getScore()
            _state.update {
                it.copy(
                    loading = false,
                    subjects = result,
                    selected = result.map { it.name }
                )
            }
            count()
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    loading = false,
                    message = e.localizedMessage
                )
            }
        }
    }

    fun select(operation: Boolean, subject: String) = viewModelScope.launch {
        if (operation) {
            _state.update {
                it.copy(
                    selected = state.value.selected + subject
                )
            }
        } else {
            _state.update {
                it.copy(
                    selected = state.value.selected - subject
                )
            }
        }
        count()
    }

    fun findByName(name: String): ScoreResult.Datas.Xscjcx.Row {
        for (subject in state.value.subjects) {
            if (subject.name == name)
                return subject
        }
        throw Exception("No subject named $name")
    }

    fun count() = viewModelScope.launch {
        var score = 0.0
        var total = 0.0
        try {
            for (name in state.value.selected) {
                val subject = findByName(name)
                (if (state.value.weight) subject.weight else 1.0).let {
                    score += subject.score * it
                    total += it
                }
            }
            _state.update {
                it.copy(
                    score = score / total,
                    message = context.getString(R.string.score_result).format(score / total)
                )
            }
        } catch (e: Exception) {
            _state.update { it.copy(message = e.localizedMessage) }
        }
    }
}