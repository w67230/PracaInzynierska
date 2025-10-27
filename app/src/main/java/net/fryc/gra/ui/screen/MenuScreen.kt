package net.fryc.gra.ui.screen

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.ui.theme.GraTheme


fun startMenu(activity: MainActivity){
    activity.setContent {
        GraTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                Menu(activity)
            }
        }
    }
}

@Composable
fun Menu(activity: MainActivity){
    Column(modifier = Modifier.padding(start = 30.dp, end = 20.dp, top = 80.dp)) {
        Text(text = stringResource(R.string.menu), modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(bottom = 50.dp), fontSize = 50.sp)

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp)) {
            AddButton(onClick = {
                activity.backStack.add {
                    startMenu(it)
                }
                customization(activity)
            }) {
                AddButtonText(text = stringResource(R.string.play))
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp)) {
            AddButton(onClick = {
                activity.backStack.add {
                    startMenu(it)
                }
                score(activity)
            }) {
                AddButtonText(text = stringResource(R.string.scores))
            }
        }

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp)) {
            AddButton(onClick = {
                activity.backStack.add {
                    startMenu(it)
                }
                howToPlay(activity)
            }) {
                AddButtonText(text = stringResource(R.string.h2p))
            }
        }

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp)) {
            AddButton(onClick = {
                activity.backStack.add {
                    startMenu(it)
                }
                settings(activity)
            }) {
                AddButtonText(text = stringResource(R.string.settings))
            }
        }
    }
}