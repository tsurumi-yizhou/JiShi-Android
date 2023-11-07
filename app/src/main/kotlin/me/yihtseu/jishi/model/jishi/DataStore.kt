package me.yihtseu.jishi.model.jishi

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataStore {

    private var dataStore : DataStore<Preferences>? = null
    private val Context.dataStore by preferencesDataStore(name = "jishi_preferences")
    fun initialize(application: Application) {
        dataStore = application.dataStore
    }

    fun getString(name: String): Flow<String?>? {
        return dataStore?.data?.map {
            it[stringPreferencesKey(name)]
        }
    }

    suspend fun setString(name: String, value: String) = dataStore?.edit {
        it[stringPreferencesKey(name)] = value
    }

    fun getStringSet(name: String): Flow<Set<String>>? {
        return dataStore?.data?.map {
            it[stringSetPreferencesKey(name)].orEmpty()
        }
    }

    suspend fun setStringSet(name: String, value: Set<String>) = dataStore?.edit {
        it[stringSetPreferencesKey(name)] = value
    }
}