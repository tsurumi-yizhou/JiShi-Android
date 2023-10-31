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
import me.yihtseu.jishi.model.jishi.Result
import me.yihtseu.jishi.repo.EduRepository
import javax.inject.Inject

@HiltViewModel
class ClassroomViewModel @Inject constructor(): ViewModel() {

    private val _buildings = MutableStateFlow<Result<List<BuildingResult.Datas.Code.Row>>>(Result.Loading)
    val buildings = _buildings.asStateFlow()
    private val _classrooms = MutableStateFlow<Result<List<RoomResult.Datas.Cxkxjs.Row>>>(Result.Loading)
    val classrooms = _classrooms.asStateFlow()

    fun query(buildingCodes: List<String>, date: String, start: Int, end: Int) = viewModelScope.launch {
        _classrooms.update { Result.Loading }
        try {
            _classrooms.update {
                Result.Success(
                    EduRepository.getClassrooms(buildingCodes, date, start, end)
                )
            }
        } catch (e: Exception) {
            _classrooms.update {
                Result.Error(e.localizedMessage)
            }
        }
    }

    fun init() = viewModelScope.launch {
        _buildings.update { Result.Loading }
        try {
            _buildings.update {
                Result.Success(
                    EduRepository.getBuildings()
                )
            }
        } catch (e: Exception) {
            Log.d("error", e.toString())
            _buildings.update { Result.Error(e.localizedMessage) }
        }
    }
}