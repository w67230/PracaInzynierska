package net.fryc.gra.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import net.fryc.gra.MainActivity


val PurpleGrey80 = Color(0xFF9BA2EC)
val Pink80 = Color(0xFFA7C2E1)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val TloJasne = Color(0xFFF9F9FF)
val GuzikiJasne = Color(0xFF5A91EF)
val TloTabela1Jasne = Color(0xFFCDE3EC)
val TloTabela2Jasne = Color(0xFFB3C6D0)
val TloTabelaHeaderJasne = Color(0xFF9DC9D7)

val Tlo = Color(0xFF242328)
val Guziki = Color(0xFF352D41)
val TloTabela1 = Color(0xFF36343B)
val TloTabela2 = Color(0xFF474552)
val TloTabelaHeader = Color(0xFF2D2C31)


@Composable
fun getButtonColor() : ButtonColors {
    if(!isSystemInDarkTheme())  return ButtonDefaults.buttonColors(containerColor = GuzikiJasne, contentColor = Color.Black)

    return ButtonDefaults.buttonColors(containerColor = Guziki, contentColor = Color.White)
}

fun getColorForNumbers(switchColors : Boolean) : Color {
    return if(switchColors) Color.White else Color.Black
}

@Composable
fun getColorForTableRow(isOdd : Boolean) : Color {
    if(isSystemInDarkTheme()) return if(isOdd) TloTabela1 else TloTabela2

    return if(isOdd) TloTabela1Jasne else TloTabela2Jasne
}

@Composable
fun getColorForTableHeader() : Color {
    return if(isSystemInDarkTheme()) TloTabelaHeader else TloTabelaHeaderJasne
}