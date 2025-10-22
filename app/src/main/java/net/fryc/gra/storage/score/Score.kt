package net.fryc.gra.storage.score

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score")
data class Score(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val movesAmount : Int,
    val timeInSeconds : Long,
    val difficulty : Int,
    val size : Int,
    val date : String
)
