package me.yihtseu.jishi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.campus.edu.ScoreResult
import me.yihtseu.jishi.model.jishi.State
import me.yihtseu.jishi.repo.EduRepository
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor() : ViewModel() {
    private val _subjects = MutableStateFlow<State<List<ScoreResult.Datas.Xscjcx.Row>>>(State.Loading)
    val subjects = _subjects.asStateFlow()
    private val _gpa = MutableStateFlow(0.0)
    val gpa = _gpa.asStateFlow()
    private val _selected = MutableStateFlow<List<String>>(emptyList())
    val selected = _selected.asStateFlow()
    private val _useWeight = MutableStateFlow<Boolean>(true)
    val useWeight = _useWeight.asStateFlow()

    fun load() = viewModelScope.launch {
        try {
            val result = EduRepository.getScore()
            _subjects.update { State.Success(result) }
            _selected.update { result.map { it.name }}
            count()
        } catch (e: Exception) {
            _subjects.update { State.Error(e.localizedMessage) }
        }
    }

    fun select(operation: Boolean, subject: String) = viewModelScope.launch {
        if (operation) {
            _selected.update { it + subject }
        } else {
            _selected.update { it - subject }
        }
        count()
    }

    fun setWeight(weight: Boolean) = viewModelScope.launch {
        _useWeight.update { weight }
    }

    fun findByName(name: String): ScoreResult.Datas.Xscjcx.Row {
        for (subject in (subjects.value as State.Success).data) {
            if (subject.name == name)
                return subject
        }
        throw Exception("No subject named $name")
    }

    fun count() = viewModelScope.launch {
        var score = 0.0
        var total = 0.0
        try {
            for (name in selected.value) {
                val subject = findByName(name)
                (if (useWeight.value) subject.weight else 1.0).let {
                    score += subject.score * it
                    total += it
                }
            }
            _gpa.update { score/total }
        } catch (e: Exception) {
            _subjects.update { State.Error(e.localizedMessage) }
        }
    }
}