package net.fryc.gra.ui.screen

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.ui.theme.GraTheme


fun score(activity: MainActivity){
    activity.setContent {
        GraTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                ScoreScreen(activity = activity)
            }
        }
    }
}

@Composable
fun ScoreScreen(activity: MainActivity){
    Column {

        AddNavigationBar(Modifier.background(Color.Red).align(Alignment.Start), {
            activity.onBackPressed()
        }, false) { }

        Column {
            if(activity.scores.isEmpty()){
                ShowSimpleText(R.string.scores_empty)
            }
            else {
                Row(Modifier.align(Alignment.CenterHorizontally)) {
                    Box { Text(stringResource(R.string.time)) }
                    Box { Text(stringResource(R.string.moves)) }
                    Box { Text(stringResource(R.string.difficulty)) }
                    Box { Text(stringResource(R.string.size)) }
                    Box { Text(stringResource(R.string.date)) }
                }

                activity.scores.forEach {
                    Row(Modifier.align(Alignment.CenterHorizontally)) {
                        Box { Text(text = it.timeInSeconds.toString()) }
                        Box { Text(text = it.movesAmount.toString()) }
                        Box { Text(text = getDifficultyName(getDifficultyFromInt(it.difficulty))) }
                        Box { Text(text = getSizeName(it.size)) }
                        Box { Text(text = it.date) }
                    }
                }
            }
        }
    }
}