package net.fryc.gra.storage.settings

import kotlinx.coroutines.flow.Flow

class SettingsRepository(private val settingsDao : SettingsDao) {

    fun getOptions() : Flow<Settings?> {
        return this.settingsDao.getOptions();
    }

    suspend fun saveOptions(settings : Settings) {
        return this.settingsDao.saveOptions(settings);
    }
}