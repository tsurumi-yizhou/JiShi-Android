package me.yihtseu.jishi.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.campus.edu.BuildingResult
import me.yihtseu.jishi.model.campus.edu.RoomResult
import me.yihtseu.jishi.model.jishi.State
import me.yihtseu.jishi.repo.EduRepository
import javax.inject.Inject

@HiltViewModel
class ClassroomViewModel @Inject constructor(): ViewModel() {

    private val _buildings = MutableStateFlow<State<List<BuildingResult.Datas.Code.Row>>>(State.Loading)
    val buildings = _buildings.asStateFlow()
    private val _classrooms = MutableStateFlow<State<List<RoomResult.Datas.Cxkxjs.Row>>>(State.Loading)
    val classrooms = _classrooms.asStateFlow()

    fun query(zone: String, code: String, date: String, start: Int, end: Int) = viewModelScope.launch {
        _classrooms.update { State.Loading }
        try {
            _classrooms.update {
                State.Success(
                    EduRepository.getClassrooms(zone, code, date, start, end)
                )
            }
        } catch (e: Exception) {
            _classrooms.update {
                State.Error(e.localizedMessage)
            }
        }
    }

    fun init() = viewModelScope.launch {
        _buildings.update { State.Loading }
        try {
            _buildings.update {
                State.Success(
                    EduRepository.getBuildings()
                )
            }
        } catch (e: Exception) {
            Log.d("error", e.toString())
            _buildings.update { State.Error(e.localizedMessage) }
        }
    }
}