package net.fryc.gra.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.fryc.gra.R
import net.fryc.gra.logic.Difficulty
import net.fryc.gra.ui.theme.getButtonColor

val PADDING_TOP_BELOW_NAV_BAR = 30.dp


@Composable
fun ShowSimpleText(resourceString : Int){
    Text(text = stringResource(resourceString), modifier = Modifier.padding(bottom = 10.dp))
}

@Composable
fun AddNavigationBar(modifier : Modifier, onBackPress : () -> Unit, showHelp : Boolean, onHelpPress : () -> Unit) {
    NavigationBar(modifier = modifier.height(50.dp)) {
        IconButton(modifier = Modifier.align(Alignment.CenterVertically), onClick = {
            onBackPress.invoke()
        }) {
            Icon(
                painter = painterResource(R.drawable.arrow_back_24px),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(32.dp)
            )
        }

        Box(Modifier.weight(1F))

        if(showHelp){
            IconButton(modifier = Modifier.align(Alignment.CenterVertically), onClick = {
                onHelpPress.invoke()
            }) {
                Text("?", fontSize = 32.sp)
            }
        }
    }
}

@Composable
fun AddOption(modifier : Modifier, description : @Composable () -> Unit, option : @Composable () -> Unit) {
    Row(modifier.fillMaxWidth(0.7F)) {
        Box(modifier = Modifier.weight(1F).align(Alignment.CenterVertically), contentAlignment = Alignment.CenterStart) {
            description.invoke()
        }

        Box(modifier = Modifier.weight(1F).align(Alignment.CenterVertically), contentAlignment = Alignment.CenterEnd) {
            option.invoke()
        }
    }
}

@Composable
fun AddButton(modifier : Modifier = Modifier, onClick : () -> Unit, content : @Composable (() -> Unit)) {
    Button(colors = getButtonColor(), modifier = modifier.fillMaxWidth(0.45F), onClick = {
        onClick.invoke()
    }) {
        content.invoke()
    }
}

@Composable
fun AddButtonText(modifier : Modifier = Modifier, text : String) {
    Text(text = text, modifier = modifier, fontWeight = FontWeight.Bold, fontSize = 24.sp)
}

fun getDifficultyFromInt(difficulty : Int): Difficulty {
    return when(difficulty){
        0 -> Difficulty.EASY
        1 -> Difficulty.NORMAL
        2 -> Difficulty.HARD
        else -> Difficulty.VERY_HARD
    }
}

@Composable
fun getDifficultyName(difficulty : Difficulty) : String {
    return when(difficulty){
        Difficulty.EASY -> stringResource(R.string.easy)
        Difficulty.NORMAL -> stringResource(R.string.normal)
        Difficulty.HARD -> stringResource(R.string.hard)
        Difficulty.VERY_HARD -> stringResource(R.string.v_hard)
    }
}

@Composable
fun getSizeName(size : Int) : String {
    return when(size){
        4 -> stringResource(R.string.size_4)
        5 -> stringResource(R.string.size_5)
        6 -> stringResource(R.string.size_6)
        else -> stringResource(R.string.size_6)
    }
}