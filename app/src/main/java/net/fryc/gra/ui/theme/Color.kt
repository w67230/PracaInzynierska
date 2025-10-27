package net.fryc.gra.ui.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val nwm = Color(0xFF6650a4)
val Tlo = Color(0xFF242328)
val Guziki = Color(0xFF352D41)


@Composable
fun getButtonColor() : ButtonColors {
    return ButtonDefaults.buttonColors(containerColor = Guziki, contentColor = Color.White)
}

fun getColorForNumbers(switchColors : Boolean) : Color {
    return if(switchColors) Color.White else Color.Black
}