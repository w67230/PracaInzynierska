package net.fryc.gra.ui.screen

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.storage.score.Score
import net.fryc.gra.ui.theme.GraTheme
import java.time.LocalDate



const val TIME_COLUMN = 0
const val MOVES_COLUMN = 1
const val DIFFICULTY_COLUMN = 2
const val SIZE_COLUMN = 3
const val DATE_COLUMN = 4


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
    var shouldShowHelp by remember { mutableStateOf(false) }

    var sortBy by remember { mutableIntStateOf(TIME_COLUMN) }
    var asc by remember { mutableStateOf(false) }

    val headerOnClick : (Int) -> Unit = {
        if(sortBy == it){
            asc = !asc
        }
        else {
            sortBy = it
        }
    }

    Column {
        AddNavigationBar(Modifier.background(Color.Red).align(Alignment.Start), {
            activity.onBackPressed()
        }, true) {
            shouldShowHelp = true
        }

        Column(Modifier.fillMaxWidth(1F).align(Alignment.CenterHorizontally).padding(top = PADDING_TOP_BELOW_NAV_BAR)) {
            if(activity.scores.isEmpty()){
                ShowSimpleText(R.string.scores_empty)
            }
            else {
                Row(Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.8F)) {
                    Box(Modifier.weight(1F).clickable(onClick = {
                        headerOnClick.invoke(TIME_COLUMN)
                    })) { Text(stringResource(R.string.time)) }
                    Box(Modifier.weight(1F).clickable(onClick = {
                        headerOnClick.invoke(MOVES_COLUMN)
                    })) { Text(stringResource(R.string.moves)) }
                    Box(Modifier.weight(1F).clickable(onClick = {
                        headerOnClick.invoke(DIFFICULTY_COLUMN)
                    })) { Text(stringResource(R.string.difficulty)) }
                    Box(Modifier.weight(1F).clickable(onClick = {
                        headerOnClick.invoke(SIZE_COLUMN)
                    })) { Text(stringResource(R.string.size)) }
                    Box(Modifier.weight(1F).clickable(onClick = {
                        headerOnClick.invoke(DATE_COLUMN)
                    })) { Text(stringResource(R.string.date)) }
                }

                LazyColumn(Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.8F)) {
                    getSortedScores(activity.scores, sortBy, asc).forEach {
                        this.item {
                            Row(Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(1F)) {
                                Box(Modifier.weight(1F)) { Text(text = it.timeInSeconds.toString()) }
                                Box(Modifier.weight(1F)) { Text(text = it.movesAmount.toString()) }
                                Box(Modifier.weight(1F)) { Text(text = getDifficultyName(getDifficultyFromInt(it.difficulty))) }
                                Box(Modifier.weight(1F)) { Text(text = getSizeName(it.size)) }
                                Box(Modifier.weight(1F)) { Text(text = it.date) }
                            }
                        }
                    }
                }
            }
        }

        if(shouldShowHelp) {
            AlertDialog(onDismissRequest = {
                shouldShowHelp = false
            }, confirmButton = {
                TextButton(onClick = {
                    shouldShowHelp = false
                }) {
                    Text(text = "Ok")
                }
            }, text = {
                var message = stringResource(R.string.help_scores)
                if(!activity.scores.isEmpty()){
                    message += stringResource(R.string.help_scores_not_empty)
                }

                Text(message)
            })
        }
    }
}

fun getSortedScores(list : List<Score>, column : Int, asc : Boolean) : List<Score> {
    if(asc){
        when(column){
            1 -> return list.sortedBy { return@sortedBy it.movesAmount }
            2 -> return list.sortedBy { return@sortedBy it.difficulty }
            3 -> return list.sortedBy { return@sortedBy it.size }
            4 -> return list.sortedBy { return@sortedBy LocalDate.parse(it.date) }
        }

        return list.sortedBy { return@sortedBy it.timeInSeconds }
    }

    return getSortedScoresDesc(list, column)
}

fun getSortedScoresDesc(list : List<Score>, column : Int) : List<Score> {
    when(column){
        1 -> return list.sortedByDescending { return@sortedByDescending it.movesAmount }
        2 -> return list.sortedByDescending { return@sortedByDescending it.difficulty }
        3 -> return list.sortedByDescending { return@sortedByDescending it.size }
        4 -> return list.sortedByDescending { return@sortedByDescending LocalDate.parse(it.date) }
    }

    return list.sortedByDescending {
        return@sortedByDescending it.timeInSeconds
    }
}
