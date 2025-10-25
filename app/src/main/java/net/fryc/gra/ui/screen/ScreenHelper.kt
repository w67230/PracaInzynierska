package net.fryc.gra.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import net.fryc.gra.R
import net.fryc.gra.logic.Difficulty
import net.fryc.gra.ui.theme.getButtonColor


@Composable
fun showSimpleText(resourceString : Int){
    Text(text = stringResource(resourceString), modifier = Modifier.padding(bottom = 10.dp));
}

@Composable
fun addNavigationBar(modifier : Modifier, onBackPress : () -> Unit, showHelp : Boolean, onHelpPress : () -> Unit) {
    NavigationBar(modifier = modifier.height(40.dp)) {
        IconButton({
            onBackPress.invoke();
        }) {
            Text("<-", fontWeight = FontWeight.Bold, fontSize = 5.em);
        }

        if(showHelp){
            IconButton({
                onHelpPress.invoke();
            }) {
                Text("?", fontWeight = FontWeight.Bold, fontSize = 5.em);
            }
        }
    }
}

@Composable
fun addButton(modifier : Modifier = Modifier, onClick : () -> Unit, content : @Composable (() -> Unit)) {
    Button(colors = getButtonColor(), modifier = modifier.fillMaxWidth(0.45F), onClick = {
        onClick.invoke();
    }) {
        content.invoke();
    }
}

@Composable
fun addButtonText(modifier : Modifier = Modifier, text : String) {
    Text(text = text, modifier = modifier, fontWeight = FontWeight.Bold, fontSize = 24.sp);
}

fun getDifficultyFromInt(difficulty : Int): Difficulty {
    return when(difficulty){
        0 -> Difficulty.EASY;
        1 -> Difficulty.NORMAL;
        2 -> Difficulty.HARD;
        else -> Difficulty.VERY_HARD;
    }
}

@Composable
fun getDifficultyName(difficulty : Difficulty) : String {
    return when(difficulty){
        Difficulty.EASY -> stringResource(R.string.easy);
        Difficulty.NORMAL -> stringResource(R.string.normal);
        Difficulty.HARD -> stringResource(R.string.hard);
        Difficulty.VERY_HARD -> stringResource(R.string.v_hard);
    }
}

@Composable
fun getSizeName(size : Int) : String {
    return when(size){
        4 -> stringResource(R.string.size_4);
        5 -> stringResource(R.string.size_5);
        6 -> stringResource(R.string.size_6);
        else -> stringResource(R.string.size_6);
    }
}