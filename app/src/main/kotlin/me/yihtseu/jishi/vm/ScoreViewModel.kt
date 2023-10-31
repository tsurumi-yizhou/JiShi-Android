package me.yihtseu.jishi.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.campus.edu.ScoreResult
import me.yihtseu.jishi.model.jishi.Result
import me.yihtseu.jishi.repo.EduRepository
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow<Result<List<ScoreResult.Datas.Xscjcx.Row>>>(Result.Loading)
    val state = _state.asStateFlow()

    fun load() = viewModelScope.launch {

            _state.update {
                Result.Success(EduRepository.getScore())
            }

    }
}