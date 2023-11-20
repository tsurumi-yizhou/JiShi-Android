package me.yihtseu.jishi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.campus.edu.EduBuildingResult
import me.yihtseu.jishi.model.campus.edu.EduRoomResult
import me.yihtseu.jishi.repo.EduRepository
import javax.inject.Inject

data class ClassroomState(
    val loading: Boolean = true,
    val message: String? = null,
    val buildings: List<EduBuildingResult.Datas.Code.Row> = emptyList(),
    val classrooms: List<EduRoomResult.Datas.Cxkxjs.Row> = emptyList()
)

@HiltViewModel
class ClassroomViewModel @Inject constructor(
    private val eduRepository: EduRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ClassroomState())
    val state = _state.asStateFlow()

    fun query(zone: String, code: String, date: String, start: Int, end: Int) = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            _state.update {
                it.copy(
                    loading = false,
                    classrooms = eduRepository.getClassrooms(zone, code, date, start, end)
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

    fun init() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            _state.update {
                it.copy(
                    loading = false,
                    buildings = eduRepository.getBuildings()
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
}