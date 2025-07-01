package net.fryc.gra.ui

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.board.Board
import net.fryc.gra.board.Difficulty
import net.fryc.gra.ui.theme.GraTheme


fun startGame(size : Int = 4, difficulty : Difficulty = Difficulty.EASY, activity: MainActivity){
    val board = Board(size, difficulty);
    activity.isInMenu = false;
    redraw(board, activity);
}
fun startMenu(activity: MainActivity){
    activity.isInMenu = true;
    activity.setContent {
        GraTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                menu(activity);
            }
        }
    }
}

fun howToPlay(activity: MainActivity){
    activity.isInMenu = false;
    activity.setContent {
        GraTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                howToPlayScreen(activity = activity);
            }
        }
    }
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
fun menu(activity: MainActivity){
    var size by remember {
        mutableIntStateOf(4);
    }
    var difficulty by remember {
        mutableStateOf(Difficulty.EASY);
    }
    Column(modifier = Modifier.padding(start = 30.dp, end = 20.dp, top = 80.dp)) {
        Text(text = stringResource(R.string.app_name), modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(bottom = 50.dp), fontSize = 50.sp);
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = stringResource(R.string.size_info), fontSize = 20.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 17.dp));
            Slider(modifier = Modifier
                .width(100.dp)
                .padding(end = 7.dp),value = size.toFloat(),valueRange = 4f..6f, steps = 3, onValueChange = {
                size = it.toInt();
            })
            val sizeString = when(size){
                4 -> stringResource(R.string.size_4);
                5 -> stringResource(R.string.size_5);
                6 -> stringResource(R.string.size_6);
                else -> stringResource(R.string.size_6);
            }
            Text(text = sizeString, fontSize = 20.sp, modifier = Modifier
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
                difficulty = when(it.toInt()){
                    0 -> Difficulty.EASY;
                    1 -> Difficulty.NORMAL;
                    2 -> Difficulty.HARD;
                    3 -> Difficulty.VERY_HARD;
                    else -> Difficulty.VERY_HARD;
                }
            });

            val diffString = when(difficulty){
                Difficulty.EASY -> stringResource(R.string.easy);
                Difficulty.NORMAL -> stringResource(R.string.normal);
                Difficulty.HARD -> stringResource(R.string.hard);
                Difficulty.VERY_HARD -> stringResource(R.string.v_hard);
            }

            Text(text = diffString, fontSize = 20.sp, modifier = Modifier
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
        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp)) {
            Button(onClick = {
                howToPlay(activity);
            }) {
                Text(text = stringResource(R.string.h2p));
            }
        }

    }

}

@Composable
fun draw(board : Board, activity: MainActivity, modifier: Modifier = Modifier){
    var shouldShowHelp by remember {
        mutableStateOf(false);
    }
    var shouldShowWarning by remember {
        mutableStateOf(false);
    }
    var y = 0;
    var x = 0;
    Column(modifier) {
        Spacer(modifier = Modifier.size(50.dp));
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            IconButton(onClick = {
                shouldShowHelp = true;
            }) {
                Text(text = "?", fontWeight = FontWeight.Bold, fontSize = 5.em);
            }
        }
        Spacer(modifier = Modifier.size(30.dp));
        while(y < board.size){
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                while(x < board.size) {
                    board.getField(x, y)?.drawBox(activity = activity);

                    x++;
                }
            }

            x = 0;
            y++;
        }

        Spacer(modifier = Modifier.size(90.dp));
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
            Button(onClick = {
                shouldShowWarning = true;
            }) {
                Text(text = stringResource(R.string.menu));
            }
        }

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

@Composable
fun howToPlayScreen(activity: MainActivity){
    var x : Int;
    var y : Int;
    LazyColumn(modifier = Modifier.padding(start = 30.dp, end = 20.dp, top = 40.dp)) {
        this.item {
            Text(text = stringResource(R.string.tytul_cel), fontWeight = FontWeight.Bold, fontSize = 6.em, modifier = Modifier.padding(bottom = 10.dp));
        }
        this.item {
            showSimpleText(resourceString = R.string.cel_gry);
        }
        this.item {
            showSimpleText(resourceString = R.string.przyklad_latwy);
        }
        this.item {
            x=0;
            y=0;
            while(y < 4){
                Row {
                    while(x < 4) {
                        Box(
                            Modifier
                                .height(70.dp)
                                .width(70.dp)
                                .padding(5.dp, 5.dp)
                                .background(
                                    color = when (y) {
                                        0 -> Color.Green;
                                        1 -> Color.Red;
                                        2 -> Color.Cyan;
                                        3 -> if (x == 3) Color.Transparent else Color.Magenta;
                                        else -> Color.Transparent;
                                    }
                                ));

                        x++;
                    }
                }

                x = 0;
                y++;
            }
        }
        this.item {
            showSimpleText(resourceString = R.string.cel_dodatkowo);
        }
        this.item {
            showSimpleText(resourceString = R.string.hard_cel_przyklad);
        }
        this.item {
            x=0;
            y=0;
            while(y < 4){
                Row {
                    while(x < 4) {
                        Box(
                            Modifier
                                .height(70.dp)
                                .width(70.dp)
                                .padding(5.dp, 5.dp)
                                .background(
                                    color = when (y) {
                                        0 -> Color.Cyan;
                                        1 -> Color.Red;
                                        2 -> Color.Green;
                                        3 -> if (x == 3) Color.Transparent else Color.Magenta;
                                        else -> Color.Transparent;
                                    }
                                )){
                            if(y < 3 || x < 3){
                                Text(text = x.toString(), modifier = Modifier.align(Alignment.Center), color = Color.Black, fontWeight = FontWeight.Bold);
                            }
                        }

                        x++;
                    }
                }

                x = 0;
                y++;
            }
        }
        this.item {
            Button(onClick = {
                startMenu(activity);
            }) {
                Text(text = "Ok");
            }
        }
    }
}

@Composable
fun showSimpleText(resourceString : Int){
    Text(text = stringResource(resourceString), modifier = Modifier.padding(bottom = 10.dp));
}