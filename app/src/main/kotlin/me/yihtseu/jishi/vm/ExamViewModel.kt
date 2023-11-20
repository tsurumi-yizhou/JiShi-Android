package me.yihtseu.jishi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.campus.EduExamResult
import me.yihtseu.jishi.repo.EduRepository
import javax.inject.Inject

data class ExamState(
    val loading: Boolean = true,
    val message: String? = null,
    val exams: List<EduExamResult.Datas.Cxxsksap.Row> = emptyList()
)

@HiltViewModel
class ExamViewModel @Inject constructor(
    val eduRepository: EduRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ExamState())
    val state = _state.asStateFlow()

    fun init() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            _state.update { it.copy(loading = false, exams = eduRepository.getExams()) }
        } catch (e: Exception) {
            _state.update { it.copy(loading = false, message = e.localizedMessage) }
        }
    }
}