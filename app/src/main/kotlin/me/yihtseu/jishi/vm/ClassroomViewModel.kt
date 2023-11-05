package me.yihtseu.jishi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.campus.edu.BuildingResult
import me.yihtseu.jishi.model.campus.edu.RoomResult
import me.yihtseu.jishi.repo.EduRepository
import javax.inject.Inject

data class ClassroomState(
    val loading: Boolean = true,
    val message: String? = null,
    val buildings: List<BuildingResult.Datas.Code.Row> = emptyList(),
    val classrooms: List<RoomResult.Datas.Cxkxjs.Row> = emptyList()
)

@HiltViewModel
class ClassroomViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ClassroomState())
    val state = _state.asStateFlow()

    fun query(zone: String, code: String, date: String, start: Int, end: Int) = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            _state.update {
                it.copy(
                    loading = false,
                    classrooms = EduRepository.getClassrooms(zone, code, date, start, end)
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
                    buildings = EduRepository.getBuildings()
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