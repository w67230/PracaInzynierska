package net.fryc.gra.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOptions(settings: Settings);

    @Query("SELECT * from settings WHERE id = 0")
    fun getOptions(): Flow<Settings>
}