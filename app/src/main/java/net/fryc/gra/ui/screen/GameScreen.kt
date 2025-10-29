package net.fryc.gra.ui.screen

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.logic.Board
import net.fryc.gra.logic.Difficulty
import net.fryc.gra.storage.score.Score
import net.fryc.gra.ui.theme.GraTheme
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

val format = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)

fun startGame(size : Int = 4, difficulty : Difficulty = Difficulty.EASY, showTimer : Boolean = true, showMoves : Boolean = true, activity: MainActivity){
    val board = Board(size, difficulty, activity.settings, showTimer, showMoves)
    redraw(board, activity)
}

fun redraw(board: Board, activity: MainActivity){
    activity.setContent {
        GraTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                Draw(board, activity)
            }
        }
    }
}

@Composable
fun Draw(board : Board, activity: MainActivity, modifier: Modifier = Modifier){
    var shouldKeepTicking by remember { mutableStateOf(true) }
    var refresh by remember { mutableStateOf(false); }

    LaunchedEffect(key1 = refresh) {
        if(shouldKeepTicking){
            delay(50)
            refresh = !refresh
        }
    }

    var shouldShowHelp by remember { mutableStateOf(false); }
    var shouldShowWarning by remember { mutableStateOf(false); }

    Column(modifier) {
        AddNavigationBar(Modifier.background(Color.Red).align(Alignment.Start), {
            shouldShowWarning = true
        }, true) {
            shouldShowHelp = true
        }

        Spacer(modifier = Modifier.size(PADDING_TOP_BELOW_NAV_BAR))

        if(board.showTimer){
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = board.getCurrentTime().toString(), fontWeight = FontWeight.Bold, fontSize = 28.sp)
            }
        }

        Spacer(modifier = Modifier.size(30.dp))

        board.fieldsMatrix.forEach {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                it.forEach { field ->
                    field.DrawBox(activity)
                }
            }
        }

        Spacer(modifier = Modifier.size(50.dp))

        if(board.showMoves){
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = stringResource(R.string.moves_done) + " " + board.moves, fontSize = 30.sp)
            }
        }

        if(board.checkWin()){
            shouldKeepTicking = false
            val time : Long = board.getCurrentTime().inWholeSeconds
            val date : String = LocalDateTime.ofInstant(board.timer.instant(), board.timer.zone).toLocalDate().toString()
            val score by remember {
                derivedStateOf { Score(
                    movesAmount = board.moves,
                    timeInSeconds = time,
                    difficulty = board.difficulty.ordinal,
                    size = board.size,
                    date = date,
                ) }
            }

            AlertDialog(onDismissRequest = {
                startMenu(activity)
            }, confirmButton = {
                TextButton(onClick = {
                    activity.viewModel?.viewModelScope?.launch {
                        val score2 : Score = score
                        activity.container?.scoreRepository?.saveScore(score2)
                    }

                    activity.backStack.clear()
                    startMenu(activity)
                }) {
                    Text(text = "Zapisz i wyjdź")
                }
            }, dismissButton = {
                TextButton(onClick = {
                    activity.backStack.clear()
                    startMenu(activity)
                }) {
                    Text(text = "Wyjdź")
                }
            }, text = {
                Text(text = stringResource(R.string.win))
            })

        }

        if(shouldShowHelp){
            AlertDialog(onDismissRequest = {
                shouldShowHelp = false
            }, confirmButton = {
                TextButton(onClick = {
                    shouldShowHelp = false
                }) {
                    Text(text = "Ok")
                }
            }, text = {
                Text(text =
                    stringResource(R.string.cel_gry) + "\n\n" + stringResource(R.string.cel_dodatkowo)
                )
            })
        }
        if(shouldShowWarning){
            AlertDialog(onDismissRequest = {
                shouldShowWarning = false
            }, confirmButton = {
                TextButton(onClick = {
                    shouldShowWarning = false
                    activity.onBackPressed()
                }) {
                    Text(text = "Tak")
                }
            }, text = {
                Text(text = stringResource(R.string.warning))
            }, dismissButton = {
                TextButton(onClick = {
                    shouldShowWarning = false
                }) {
                    Text(text = "Nie")
                }
            })
        }
    }
}