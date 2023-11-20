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
import me.yihtseu.jishi.model.campus.edu.EduLessonResult
import me.yihtseu.jishi.model.campus.edu.EduTermResult
import me.yihtseu.jishi.repo.EduRepository
import me.yihtseu.jishi.utils.system.checkEvent
import me.yihtseu.jishi.utils.system.insertEvent
import me.yihtseu.jishi.utils.time.endTime
import me.yihtseu.jishi.utils.time.parse
import me.yihtseu.jishi.utils.time.startTime
import me.yihtseu.jishi.utils.time.weeksPast
import javax.inject.Inject

data class CalendarState(
    val loading: Boolean = true,
    val message: String? = null,
    val term: EduTermResult.Datas.Cxjcs.Row? = null,
    val lessons: List<EduLessonResult.Datas.studentLessonTable.Row> = emptyList(),
)

@HiltViewModel
class CalendarViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val eduRepository: EduRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CalendarState())
    val state = _state.asStateFlow()

    fun fetchTerm() = viewModelScope.launch {

    }

    fun init() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val term = eduRepository.getTerm().latest()
            val lessons = eduRepository.getLessons(term.yearRange, term.term, weeksPast(term.startDate))
            _state.update {
                it.copy(
                    loading = false,
                    term = term,
                    lessons = lessons
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

    fun setCalendar() = viewModelScope.launch {
        try {
            state.value.lessons.forEach {
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
        } catch (e: Exception) {
            _state.update { it.copy(message = e.localizedMessage) }
        }
    }
}