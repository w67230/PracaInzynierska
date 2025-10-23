package net.fryc.gra.storage.settings

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val moveBlocksWithClick : Boolean,
    val multiMoveWithCLick : Boolean,

    val firstRed : Float,
    val firstGreen : Float,
    val firstBlue : Float,

    val secondRed : Float,
    val secondGreen : Float,
    val secondBlue : Float,

    val thirdRed : Float,
    val thirdGreen : Float,
    val thirdBlue : Float,

    val fourthRed : Float,
    val fourthGreen : Float,
    val fourthBlue : Float,

    val fifthRed : Float,
    val fifthGreen : Float,
    val fifthBlue : Float,

    val sixthRed : Float,
    val sixthGreen : Float,
    val sixthBlue : Float
)
