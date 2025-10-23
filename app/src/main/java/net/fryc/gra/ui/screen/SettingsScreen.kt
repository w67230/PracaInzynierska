package net.fryc.gra.ui.screen

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.storage.settings.Settings
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

    var chosenBlock by remember { mutableIntStateOf(0) }

    var first by remember {
        mutableStateOf(
            Color(activity.settings.firstRed, activity.settings.firstGreen, activity.settings.firstBlue)
        )
    }
    var second by remember {
        mutableStateOf(
            Color(activity.settings.secondRed, activity.settings.secondGreen, activity.settings.secondBlue)
        )
    }
    var third by remember {
        mutableStateOf(
            Color(activity.settings.thirdRed, activity.settings.thirdGreen, activity.settings.thirdBlue)
        )
    }
    var fourth by remember {
        mutableStateOf(
            Color(activity.settings.fourthRed, activity.settings.fourthGreen, activity.settings.fourthBlue)
        )
    }
    var fifth by remember {
        mutableStateOf(
            Color(activity.settings.fifthRed, activity.settings.fifthGreen, activity.settings.fifthBlue)
        )
    }
    var sixth by remember {
        mutableStateOf(
            Color(activity.settings.sixthRed, activity.settings.sixthGreen, activity.settings.sixthBlue)
        )
    }

    Column {

        addNavigationBar(Modifier.background(Color.Red).align(Alignment.Start), {
            activity.container?.viewModelScope?.launch {
                activity.container?.settingsRepository?.saveOptions(Settings(
                    0,
                    moveBlocksWithClick,
                    false,
                    first.red, first.green, first.blue,
                    second.red, second.green, second.blue,
                    third.red, third.green, third.blue,
                    fourth.red, fourth.green, fourth.blue,
                    fifth.red, fifth.green, fifth.blue,
                    sixth.red, sixth.green, sixth.blue
                ));

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

            var red by remember { mutableFloatStateOf(0.5f) }
            var green by remember { mutableFloatStateOf(0.5f) }
            var blue by remember { mutableFloatStateOf(0.5f) }

            val color = Color(red, green, blue);

            Column(Modifier.align(Alignment.CenterHorizontally)) {
                Row { showSimpleText(R.string.block_colors); }
                Row {
                    createColorBlock(if(chosenBlock == 1) color else first, chosenBlock == 1) {
                        red = first.red;
                        green = first.green;
                        blue = first.blue;
                        chosenBlock = 1
                    }
                    createColorBlock(if(chosenBlock == 2) color else second, chosenBlock == 2) {
                        red = second.red;
                        green = second.green;
                        blue = second.blue;
                        chosenBlock = 2
                    }
                    createColorBlock(if(chosenBlock == 3) color else third, chosenBlock == 3) {
                        red = third.red;
                        green = third.green;
                        blue = third.blue;
                        chosenBlock = 3
                    }
                    createColorBlock(if(chosenBlock == 4) color else fourth, chosenBlock == 4) {
                        red = fourth.red;
                        green = fourth.green;
                        blue = fourth.blue;
                        chosenBlock = 4
                    }
                    createColorBlock(if(chosenBlock == 5) color else fifth, chosenBlock == 5) {
                        red = fifth.red;
                        green = fifth.green;
                        blue = fifth.blue;
                        chosenBlock = 5
                    }
                    createColorBlock(if(chosenBlock == 6) color else sixth, chosenBlock == 6) {
                        red = sixth.red;
                        green = sixth.green;
                        blue = sixth.blue;
                        chosenBlock = 6
                    }
                }

                if(chosenBlock > 0){
                    Row {
                        Column {
                            Text(stringResource(R.string.red));
                            createColorSlider(red) { red = it }
                        }
                        Column {
                            Text(stringResource(R.string.green));
                            createColorSlider(green) { green = it }
                        }
                        Column {
                            Text(stringResource(R.string.blue));
                            createColorSlider(blue) { blue = it }
                        }
                    }

                    Row {
                        Button({
                            chosenBlock = 0;
                        }) { Text(stringResource(R.string.cancel)) }

                        Button({
                            when(chosenBlock) {
                                1 -> first = color;
                                2 -> second = color;
                                3 -> third = color;
                                4 -> fourth = color;
                                5 -> fifth = color;
                                6 -> sixth = color;
                            }
                            chosenBlock = 0;
                        }) { Text(stringResource(R.string.save)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun createColorSlider(value : Float, onValueChanged : (Float) -> Unit) {
    Slider(
        modifier = Modifier.width(100.dp).padding(end = 7.dp),
        value = value,
        valueRange = 0f..1f,
        steps = 101,
        onValueChange = {
        onValueChanged.invoke(it);
    });
}

@Composable
private fun createColorBlock(color : Color, chosen : Boolean, onClick : () -> Unit) {
    Box(modifier = Modifier
        .background(color)
        .width(50.dp)
        .height(50.dp)
        .border(5.dp, if(chosen) Color.Red else Color.Transparent)
        .clickable(onClick = {
        onClick.invoke();
    }));
}