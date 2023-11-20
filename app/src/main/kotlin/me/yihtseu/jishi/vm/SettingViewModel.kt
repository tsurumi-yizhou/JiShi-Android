package me.yihtseu.jishi.vm

import android.content.Context
import android.icu.util.Calendar
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drake.net.Get
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import me.yihtseu.jishi.BuildConfig
import me.yihtseu.jishi.R
import me.yihtseu.jishi.base.DataStore
import me.yihtseu.jishi.model.jishi.Version
import me.yihtseu.jishi.utils.time.parse
import javax.inject.Inject

data class SettingState(
    val message: String? = null,
    val url: Uri? = null,
    val heartbeat: Long = 0,
    val timeout: Long = 0
)

@HiltViewModel
class SettingViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    fun setHeartbeat(heartbeat: Long) = viewModelScope.launch {
        try {
            DataStore.setNumber(heartbeatKey, heartbeat)
        } catch (e: Exception) {
            _state.update { it.copy(message = e.localizedMessage) }
        }
    }

    fun setTimeout(timeout: Long) = viewModelScope.launch {
        try {
            DataStore.setNumber(timeoutKey, timeout)
            _state.update { it.copy(timeout = timeout, message = "重启后生效") }
        } catch (e: Exception) {
            _state.update { it.copy(message = e.localizedMessage) }
        }
    }

    fun checkUpdate() = viewModelScope.launch {
        try {
            val version = Json.decodeFromString<Version>(Get<String>("https://cdn.yizhou.ac.cn/metadata.json").await())
            val timestamp = version.createdTimestamp
            val remote = Calendar.getInstance().apply {
                timeInMillis = timestamp
            }.time
            val local = Calendar.getInstance().apply {
                timeInMillis = parse("yyyy-MM-dd", BuildConfig.BUILD_TIME)
            }.time
            if (local.date < remote.date) {
                _state.update {
                    it.copy(
                        message = context.getString(R.string.new_version),
                        url = Uri.parse(version.getUrl(BuildConfig.BUILD_NAME))
                    )
                }
            } else {
                _state.update { it.copy(message = context.getString(R.string.no_new_version)) }
            }
        } catch (e: Exception) {
            _state.update { it.copy(message = e.localizedMessage) }
        }
    }

    fun init() = viewModelScope.launch {
        DataStore.getNumber(heartbeatKey)?.collect {
            it?.let { duration ->
                _state.update { it.copy(heartbeat = duration) }
            }
        }
        DataStore.getNumber(timeoutKey)?.collect {
            it?.let { timeout ->
                _state.update { it.copy(timeout = timeout) }
            }
        }
    }

    companion object {
        val heartbeatKey = "heartbeat"
        val timeoutKey = "timeout"
    }
}