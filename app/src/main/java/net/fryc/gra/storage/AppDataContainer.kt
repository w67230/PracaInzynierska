package net.fryc.gra.storage

import android.content.Context
import androidx.lifecycle.ViewModel
import net.fryc.gra.storage.score.ScoreRepository
import net.fryc.gra.storage.settings.SettingsRepository

class AppDataContainer(private val context : Context) : ViewModel() {

    val settingsRepository : SettingsRepository by lazy {
        SettingsRepository(GameDatabase.getDatabase(this.context).settingsDao());
    }

    val scoreRepository : ScoreRepository by lazy {
        ScoreRepository(GameDatabase.getDatabase(this.context).scoreDao());
    }
}