package net.fryc.gra.ui.screen

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.storage.Settings
import net.fryc.gra.ui.theme.GraTheme

fun settings(activity: MainActivity){
    activity.isInMenu = false;
    activity.setContent {
        GraTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                settingsScreen(activity = activity);
            }
        }
    }
}

@Composable
fun settingsScreen(activity: MainActivity){
    var moveBlocksWithClick by remember {
        mutableStateOf(activity.settings.moveBlocksWithClick)
    }
    var multiMoveBlocksWithClick by remember { mutableStateOf(false) }

    Column {

        addNavigationBar(Modifier.background(Color.Red).align(Alignment.Start), {
            activity.container?.viewModelScope?.launch {
                activity.container?.settingsRepository?.saveOptions(Settings(0, moveBlocksWithClick, false))

            }.also {
                activity.updateSettings();
            }
            startMenu(activity);
        }, false) { }

        Column {
            Row(Modifier.align(Alignment.CenterHorizontally)) {
                showSimpleText(R.string.move_with_click);
                Switch(checked = moveBlocksWithClick, onCheckedChange = {
                    moveBlocksWithClick = !moveBlocksWithClick;
                })
            }

            Row(Modifier.align(Alignment.CenterHorizontally)) {
                showSimpleText(R.string.multi_move_with_click);
                Switch(checked = multiMoveBlocksWithClick, enabled = moveBlocksWithClick, onCheckedChange = {
                    multiMoveBlocksWithClick = !multiMoveBlocksWithClick;
                })
            }
        }
    }


}