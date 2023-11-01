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
import me.yihtseu.jishi.model.campus.edu.LessonResult
import me.yihtseu.jishi.model.campus.edu.TermResult
import me.yihtseu.jishi.model.jishi.State
import me.yihtseu.jishi.repo.EduRepository
import me.yihtseu.jishi.utils.system.checkEvent
import me.yihtseu.jishi.utils.system.insertEvent
import me.yihtseu.jishi.utils.time.endTime
import me.yihtseu.jishi.utils.time.parse
import me.yihtseu.jishi.utils.time.startTime
import me.yihtseu.jishi.utils.time.weeksPast
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _lessons = MutableStateFlow<State<List<LessonResult.Datas.studentLessonTable.Row>>>(State.Loading)
    val lessons = _lessons.asStateFlow()
    private val _term = MutableStateFlow<State<TermResult.Datas.Cxjcs.Row>>(State.Loading)
    val term = _term.asStateFlow()

    fun fetchTerm() = viewModelScope.launch {

    }

    fun init() = viewModelScope.launch {
        _lessons.update { State.Loading }
        _term.update { State.Loading }
        try {
            val termInfo = EduRepository.getTerm().latest()
            _term.update { State.Success(termInfo) }
            _lessons.update {
                State.Success(
                    EduRepository.getLessons(
                        termInfo.yearRange,
                        termInfo.term,
                        weeksPast(termInfo.startDate)
                    )
                )
            }
        } catch (e: Exception) {
            _lessons.update {
                State.Error(e.localizedMessage)
            }
        }
    }

    fun setCalendar() = viewModelScope.launch {
        try {
            if (lessons.value is State.Success) {
                (_lessons.value as State.Success).data.forEach {
                    val date = parse("yyyy年MM月dd日", "2023年${it.date}")
                    if (!checkEvent(
                            context,
                            it.lessonName,
                            date + startTime(it.startLessonNum),
                            date + endTime(it.endLessonNum)
                        )
                    ) {
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
            }
        } catch (e: Exception) {
            State.Error(e.localizedMessage)
        }
    }
}