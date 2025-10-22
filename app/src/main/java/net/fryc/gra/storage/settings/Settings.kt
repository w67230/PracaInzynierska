package net.fryc.gra.storage.settings

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val moveBlocksWithClick : Boolean,
    val multiMoveWithCLick : Boolean
)
