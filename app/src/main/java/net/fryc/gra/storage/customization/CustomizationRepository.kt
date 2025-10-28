package net.fryc.gra.storage.customization

import kotlinx.coroutines.flow.Flow

class CustomizationRepository(private val customizationDao : CustomizationDao) {

    fun getLastCustomization() : Flow<Customization?> {
        return this.customizationDao.getLastCustomization()
    }

    suspend fun saveCustomization(customization : Customization) {
        return this.customizationDao.saveCustomization(customization)
    }
}