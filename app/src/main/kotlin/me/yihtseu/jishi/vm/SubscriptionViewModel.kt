package me.yihtseu.jishi.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huawei.hms.push.HmsMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.yihtseu.jishi.R
import me.yihtseu.jishi.model.jishi.DataStore
import javax.inject.Inject

data class SubscriptionState(
    val loading: Boolean = false,
    val message: String? = null,
    val topics: Set<String> = emptySet()
)

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(SubscriptionState())
    val state = _state.asStateFlow()
    fun load() = viewModelScope.launch {
        DataStore.getStringSet("subscription_topics").collect { set ->
            set?.let {
                _state.update {
                    it.copy(topics = set)
                }
            }
        }
    }

    fun add(topic: String) = viewModelScope.launch {
        HmsMessaging.getInstance(context).subscribe(topic)
            .addOnSuccessListener {
                _state.update {
                    it.copy(message = context.getString(R.string.success), topics = state.value.topics + topic)
                }
            }
            .addOnFailureListener { error ->
                _state.update {
                    it.copy(message = error.localizedMessage)
                }
            }
        DataStore.setStringSet("subscription_topics", state.value.topics)
    }
}