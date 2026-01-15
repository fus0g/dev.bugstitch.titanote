package dev.bugstitch.titanote.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.bugstitch.titanote.utils.Constants
import kotlinx.coroutines.flow.first
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

    suspend fun encryptionExists():Boolean{

        val preference = context.datastore.data.first()
        return preference.contains(encryption)
    }

    suspend fun setEncryptionState(enabled:Boolean){
        context.datastore.edit { settings ->
            settings[encryption] = enabled
        }
    }

    suspend fun getEncryptionState():Boolean?{
        val preference = context.datastore.data.first()
        return preference[encryption]
    }
    suspend fun setSalt(s:String){
        context.datastore.edit { settings ->
            settings[salt] = s
        }
    }
    suspend fun getSalt():String?{
        val preference = context.datastore.data.first()
        return preference[salt]
    }



    companion object{
        val autosaveKey = booleanPreferencesKey(Constants.PREFERENCE_AUTOSAVE)
        val encryption = booleanPreferencesKey(Constants.PREFERENCE_ENCRYPTED)
        val salt = stringPreferencesKey(Constants.PREFERENCE_SALT)
    }
}