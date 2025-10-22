package net.fryc.gra.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Settings::class], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var Instance: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GameDatabase::class.java, "game_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }

    abstract fun settingsDao(): SettingsDao;

}