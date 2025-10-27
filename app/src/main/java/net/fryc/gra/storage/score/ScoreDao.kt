package net.fryc.gra.storage.score

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveScore(score: Score)

    @Query("SELECT * from score")
    fun getAllScores(): Flow<List<Score>>
}