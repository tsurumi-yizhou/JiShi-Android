package me.yihtseu.jishi.vm

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import me.yihtseu.jishi.R
import me.yihtseu.jishi.repo.GithubRepository
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val githubRepository: GithubRepository
) : ViewModel() {
    fun checkUpdate(host: SnackbarHostState) = viewModelScope.launch {
        try {
            val latest = githubRepository.fetchLatestRelease("formal")
            val result = host.showSnackbar(
                message = latest.name,
                actionLabel = "Download",
                duration = SnackbarDuration.Indefinite
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {}
                SnackbarResult.Dismissed -> {
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse(latest.assets.first().browserDownloadUrl)
                    }
                    context.startActivity(intent)
                }
            }
        } catch (e: Exception) {
            host.showSnackbar(context.getString(R.string.update_error))
        }
    }
}