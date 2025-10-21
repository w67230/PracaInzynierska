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
import androidx.compose.ui.unit.em
import kotlinx.coroutines.delay
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.board.Board
import net.fryc.gra.board.Difficulty
import net.fryc.gra.ui.theme.GraTheme
import java.sql.Time
import java.time.Clock
import java.time.Duration
import kotlin.time.Duration.Companion.milliseconds


fun startGame(size : Int = 4, difficulty : Difficulty = Difficulty.EASY, activity: MainActivity){
    val board = Board(size, difficulty);
    activity.isInMenu = false;
    redraw(board, activity);
}

fun redraw(board: Board, activity: MainActivity){
    activity.isInMenu = false;
    activity.setContent {
        GraTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                draw(board, activity);
            }
        }
    }
}

@Composable
fun draw(board : Board, activity: MainActivity, modifier: Modifier = Modifier){
    var refresh by remember {
        mutableStateOf(false);
    }
    LaunchedEffect(key1 = refresh) {
        delay(50);
        refresh = !refresh;
    }
    val clock by remember {
        derivedStateOf { Clock.tick(Clock.systemDefaultZone(), Duration.ofSeconds(1)) }
    }
    val startTime by remember {
        derivedStateOf { clock.millis() }
    }

    var shouldShowHelp by remember {
        mutableStateOf(false);
    }
    var shouldShowWarning by remember {
        mutableStateOf(false);
    }

    Column(modifier) {
        addNavigationBar(Modifier.background(Color.Red).align(Alignment.Start), {
            shouldShowWarning = true;
        }, true) {
            shouldShowHelp = true;
        }

        Spacer(modifier = Modifier.size(50.dp));
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = Time.from(clock.instant()).time.minus(startTime).milliseconds.toString(), fontWeight = FontWeight.Bold, fontSize = 5.em);
        }
        Spacer(modifier = Modifier.size(30.dp));

        board.fieldsMatrix.forEach {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                it.forEach { field ->
                    field.drawBox(activity);
                }
            }
        }

        Spacer(modifier = Modifier.size(90.dp));

        if(board.checkWin()){
            AlertDialog(onDismissRequest = {
                startMenu(activity);
            }, confirmButton = {
                TextButton(onClick = {
                    startMenu(activity);
                }) {
                    Text(text = "Ok");
                }
            }, text = {
                Text(text = stringResource(R.string.win));
            });

        }

        if(shouldShowHelp){
            AlertDialog(onDismissRequest = {
                shouldShowHelp = false;
            }, confirmButton = {
                TextButton(onClick = {
                    shouldShowHelp = false;
                }) {
                    Text(text = "Ok");
                }
            }, text = {
                Text(text =
                    stringResource(R.string.cel_gry) + "\n\n" + stringResource(R.string.cel_dodatkowo)
                );
            });
        }
        if(shouldShowWarning){
            AlertDialog(onDismissRequest = {
                shouldShowWarning = false;
            }, confirmButton = {
                TextButton(onClick = {
                    shouldShowWarning = false;
                    startMenu(activity);
                }) {
                    Text(text = "Tak");
                }
            }, text = {
                Text(text = stringResource(R.string.warning));
            }, dismissButton = {
                TextButton(onClick = {
                    shouldShowWarning = false;
                }) {
                    Text(text = "Nie");
                }
            });
        }
    }
}