package net.fryc.gra.storage

import android.content.Context
import androidx.lifecycle.ViewModel

class AppDataContainer(private val context : Context) : ViewModel() {

    val settingsRepository : SettingsRepository by lazy {
        SettingsRepository(GameDatabase.getDatabase(this.context).settingsDao());
    }
}