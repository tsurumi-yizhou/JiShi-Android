package me.yihtseu.jishi.vm

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.model.campus.edu.LessonResult
import me.yihtseu.jishi.model.jishi.Result
import me.yihtseu.jishi.repo.EduRepository
import me.yihtseu.jishi.utils.system.checkEvent
import me.yihtseu.jishi.utils.system.insertEvent
import me.yihtseu.jishi.utils.time.endTime
import me.yihtseu.jishi.utils.time.startTime
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _state = MutableStateFlow<Result<List<LessonResult.Datas.studentLessonTable.Row>>>(Result.Loading)
    val state = _state.asStateFlow()

    fun fetchLessons() = viewModelScope.launch {
        _state.update { Result.Loading }
        try {
            _state.update {
                Result.Success(EduRepository.getLessons())
            }
        } catch (e: Exception) {
            _state.update {
                Result.Error(e.localizedMessage)
            }
        }
    }

    fun setCalendar() = viewModelScope.launch {

            if (_state.value is Result.Success) {
                Thread {
                    (_state.value as Result.Success).data.forEach {
                        val date = SimpleDateFormat("yyyy年MM月dd日").parse("${it.year}年${it.date}").time
                        if (
                            !checkEvent(context, it.lessonName, date + startTime(it.startLessonNum), date + endTime(it.endLessonNum))
                        ) {
                            Log.d("insert", it.toString())
                            insertEvent(
                                context,
                                it.lessonName,
                                date + startTime(it.startLessonNum),
                                date + endTime(it.endLessonNum),
                                it.classroomName.orEmpty(),
                                it.teacherName.orEmpty()
                            )
                        }
                    }
                }.start()
            }

    }
}