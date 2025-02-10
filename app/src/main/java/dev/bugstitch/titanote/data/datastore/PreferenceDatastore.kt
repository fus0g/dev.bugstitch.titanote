package dev.bugstitch.titanote.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dev.bugstitch.titanote.utils.Constants
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceDatastore @Inject constructor(private val context: Context) {

    val Context.datastore by preferencesDataStore(name = Constants.DATASTORE_PREFERENCE)

    val autosavePreferences = context.datastore.data.map { it[autosaveKey] ?: true }

    suspend fun updateAutosaveKey(value: Boolean)
    {
        context.datastore.edit { settings->
            settings[autosaveKey] = value
        }
    }

    companion object{
        val autosaveKey = booleanPreferencesKey(Constants.PREFERENCE_AUTOSAVE)
    }
}