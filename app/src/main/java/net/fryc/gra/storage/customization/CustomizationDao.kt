package net.fryc.gra.storage.customization

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomizationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCustomization(customization: Customization)

    @Query("SELECT * from customization WHERE id = 0")
    fun getLastCustomization(): Flow<Customization?>
}