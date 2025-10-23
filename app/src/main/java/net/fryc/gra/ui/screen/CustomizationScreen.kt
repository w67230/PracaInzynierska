package net.fryc.gra.ui.screen

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.board.Difficulty
import net.fryc.gra.ui.theme.GraTheme


fun customization(activity: MainActivity){
    activity.isInMenu = false;
    activity.setContent {
        GraTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                customizationScreen(activity);
            }
        }
    }
}

@Composable
fun customizationScreen(activity: MainActivity) {
    var size by remember {
        mutableIntStateOf(4);
    }
    var difficulty by remember {
        mutableStateOf(Difficulty.EASY);
    }
    Column {

        addNavigationBar(Modifier.background(Color.Red).align(Alignment.Start), {
            startMenu(activity);
        }, false) { }

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = stringResource(R.string.size_info), fontSize = 20.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 17.dp));

            Slider(modifier = Modifier
                .width(100.dp)
                .padding(end = 7.dp),value = size.toFloat(),valueRange = 4f..6f, steps = 3, onValueChange = {
                size = it.toInt();
            })

            Text(text = getSizeName(size), fontSize = 20.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(0.6f));
        }

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = stringResource(R.string.difficulty_info), fontSize = 20.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 7.dp));
            Slider(modifier = Modifier
                .width(100.dp)
                .padding(end = 7.dp),value = difficulty.ordinal.toFloat(),valueRange = 0f..3f, steps = 4, onValueChange = {
                difficulty = getDifficultyFromInt(it.toInt());
            });

            Text(text = getDifficultyName(difficulty), fontSize = 20.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(0.6f));

        }

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp)) {
            Button(onClick = {
                startGame(size, difficulty, activity);
            }) {
                Text(text = stringResource(R.string.start));
            }
        }
    }
}