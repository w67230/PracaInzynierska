package net.fryc.gra.ui.screen

import androidx.activity.compose.setContent
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.board.Difficulty
import net.fryc.gra.ui.theme.GraTheme


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

@Composable
fun menu(activity: MainActivity){
    Column(modifier = Modifier.padding(start = 30.dp, end = 20.dp, top = 80.dp)) {
        Text(text = stringResource(R.string.app_name), modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(bottom = 50.dp), fontSize = 50.sp);

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp)) {
            Button(onClick = {
                customization(activity);
            }) {
                Text(text = stringResource(R.string.play));
            }
        }

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp)) {
            Button(onClick = {
                score(activity);
            }) {
                Text(text = stringResource(R.string.scores));
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

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp)) {
            Button(onClick = {
                settings(activity);
            }) {
                Text(text = stringResource(R.string.settings));
            }
        }
    }
}