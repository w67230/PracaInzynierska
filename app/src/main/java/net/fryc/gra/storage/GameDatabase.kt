package net.fryc.gra.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.fryc.gra.storage.customization.Customization
import net.fryc.gra.storage.customization.CustomizationDao
import net.fryc.gra.storage.score.Score
import net.fryc.gra.storage.score.ScoreDao
import net.fryc.gra.storage.settings.Settings
import net.fryc.gra.storage.settings.SettingsDao

@Database(entities = [Settings::class, Score::class, Customization::class], version = 5, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var Instance: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GameDatabase::class.java, "game_database")
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { Instance = it }
            }
        }
    }

    abstract fun settingsDao(): SettingsDao

    abstract fun scoreDao(): ScoreDao

    abstract fun customizationDao(): CustomizationDao

}