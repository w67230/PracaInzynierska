package net.fryc.gra.storage.customization

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customization")
data class Customization(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val difficulty : Int,
    val size : Int,
    val showTimer : Boolean,
    val showMoves : Boolean
)
