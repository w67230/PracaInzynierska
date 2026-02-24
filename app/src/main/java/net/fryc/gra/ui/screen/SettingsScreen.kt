package net.fryc.gra.ui.screen

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.storage.settings.Settings
import net.fryc.gra.ui.theme.GraTheme
import net.fryc.gra.ui.theme.Guziki
import net.fryc.gra.ui.theme.GuzikiJasne
import net.fryc.gra.ui.theme.OPTION_FONT_SIZE
import net.fryc.gra.ui.theme.PurpleGrey80
import net.fryc.gra.ui.theme.SMALLER_OPTION_FONT_SIZE
import net.fryc.gra.ui.theme.getButtonColor
import net.fryc.gra.ui.theme.getColorForNumbers
import kotlin.random.Random

val DEFAULT_SETTINGS : Settings = Settings(
    0,
    false, false, true, false,
    0.8F, 0.06F, 0.08F,
    0.13F, 0.85F, 0.1F,
    0.15F, 0.35F, 0.7F,
    0.74F, 0.15F, 0.72F,
    0.6F, 0.4F, 0.39F,
    0.875F, 0.4F, 0.11F
)

fun settings(activity: MainActivity){
    activity.setContent {
        GraTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                SettingsScreen(activity = activity)
            }
        }
    }
}

@Composable
fun SettingsScreen(activity: MainActivity){
    val fontSize = getAppropriateSize(SMALLER_OPTION_FONT_SIZE, OPTION_FONT_SIZE)

    var sthChanged by remember { mutableStateOf(false) }
    var shouldShowWarning by remember { mutableStateOf(false) }
    
    var moveBlocksWithClick by remember {
        mutableStateOf(activity.settings.moveBlocksWithClick)
    }
    var multiMoveBlocksWithClick by remember { mutableStateOf(activity.settings.multiMoveWithCLick) }

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

    var addNumberBorder by remember { mutableStateOf(activity.settings.addNumberBorder) }
    var switchNumbersColor by remember { mutableStateOf(activity.settings.switchNumbersColor) }

    val changeCheck = {
        sthChanged = !nothingChanged(
            activity.settings,
            moveBlocksWithClick,
            multiMoveBlocksWithClick, switchNumbersColor, addNumberBorder,
            first,
            second,
            third,
            fourth,
            fifth,
            sixth
        )
    }

    Column {

        AddNavigationBar(Modifier.background(Color.Red).align(Alignment.Start), {
            if(sthChanged){
                shouldShowWarning = true
            }
            else {
                activity.onBackPressed()
            }
        }, false) { }



        LazyColumn(Modifier.fillMaxWidth(1F)) {

            this.item {
                Spacer(Modifier.size(PADDING_TOP_BELOW_NAV_BAR))
            }

            this.item {
                Column(Modifier.fillMaxWidth(1F)) {
                    AddOption(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp), description = {
                        Text(stringResource(R.string.move_with_click), fontSize = fontSize, modifier = Modifier
                            .padding(end = 20.dp, start = 5.dp))
                    }) {
                        Switch(modifier = Modifier.padding(end = 5.dp, start = 20.dp), checked = moveBlocksWithClick, onCheckedChange = {
                            moveBlocksWithClick = !moveBlocksWithClick
                            changeCheck.invoke()
                        })
                    }
                }
            }

            this.item {
                Column(Modifier.fillMaxWidth(1F)) {
                    AddOption(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp), description = {
                        Text(stringResource(R.string.multi_move_with_click), fontSize = fontSize, modifier = Modifier
                            .padding(end = 20.dp, start = 5.dp))
                    }) {
                        Switch(modifier = Modifier.padding(start = 20.dp, end = 5.dp), checked = multiMoveBlocksWithClick, enabled = moveBlocksWithClick, onCheckedChange = {
                            multiMoveBlocksWithClick = !multiMoveBlocksWithClick
                            changeCheck.invoke()
                        })
                    }
                }
            }

            this.item {
                Column(Modifier.fillMaxWidth(1F)) {
                    AddOption(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp), description = {
                        Text(stringResource(R.string.addNumberBorder), fontSize = fontSize, modifier = Modifier
                            .padding(end = 20.dp, start = 5.dp))
                    }) {
                        Switch(modifier = Modifier.padding(start = 20.dp, end = 5.dp), checked = addNumberBorder, onCheckedChange = {
                            addNumberBorder = !addNumberBorder
                            changeCheck.invoke()
                        })
                    }
                }
            }

            this.item {
                Column(Modifier.fillMaxWidth(1F)) {
                    AddOption(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp), description = {
                        Text(stringResource(R.string.switchNumberColor), fontSize = fontSize, modifier = Modifier
                            .padding(end = 20.dp, start = 5.dp))
                    }) {
                        Switch(modifier = Modifier.padding(start = 20.dp, end = 5.dp), checked = switchNumbersColor, onCheckedChange = {
                            switchNumbersColor = !switchNumbersColor
                            changeCheck.invoke()
                        })
                    }
                }
            }

            this.item {
                Spacer(Modifier.size(SMALL_SPACER))

                var red by remember { mutableFloatStateOf(0.5f) }
                var green by remember { mutableFloatStateOf(0.5f) }
                var blue by remember { mutableFloatStateOf(0.5f) }

                val color = Color(red, green, blue)

                Column(Modifier.fillMaxWidth(1F)) {
                    Column(Modifier.align(Alignment.CenterHorizontally)) {
                        Row(Modifier.align(Alignment.CenterHorizontally)) { ShowSimpleText(R.string.block_colors) }
                        Row {
                            CreateColorBlock(if(chosenBlock == 1) color else first, chosenBlock == 1, addNumberBorder, switchNumbersColor) {
                                red = first.red
                                green = first.green
                                blue = first.blue
                                chosenBlock = 1
                            }
                            CreateColorBlock(if(chosenBlock == 2) color else second, chosenBlock == 2, addNumberBorder, switchNumbersColor) {
                                red = second.red
                                green = second.green
                                blue = second.blue
                                chosenBlock = 2
                            }
                            CreateColorBlock(if(chosenBlock == 3) color else third, chosenBlock == 3, addNumberBorder, switchNumbersColor) {
                                red = third.red
                                green = third.green
                                blue = third.blue
                                chosenBlock = 3
                            }
                            CreateColorBlock(if(chosenBlock == 4) color else fourth, chosenBlock == 4, addNumberBorder, switchNumbersColor) {
                                red = fourth.red
                                green = fourth.green
                                blue = fourth.blue
                                chosenBlock = 4
                            }
                            CreateColorBlock(if(chosenBlock == 5) color else fifth, chosenBlock == 5, addNumberBorder, switchNumbersColor) {
                                red = fifth.red
                                green = fifth.green
                                blue = fifth.blue
                                chosenBlock = 5
                            }
                            CreateColorBlock(if(chosenBlock == 6) color else sixth, chosenBlock == 6, addNumberBorder, switchNumbersColor) {
                                red = sixth.red
                                green = sixth.green
                                blue = sixth.blue
                                chosenBlock = 6
                            }
                        }

                        if(chosenBlock > 0){
                            Row {
                                Column {
                                    Text(stringResource(R.string.red))
                                    CreateColorSlider(red) { red = it }
                                }
                                Column {
                                    Text(stringResource(R.string.green))
                                    CreateColorSlider(green) { green = it }
                                }
                                Column {
                                    Text(stringResource(R.string.blue))
                                    CreateColorSlider(blue) { blue = it }
                                }
                            }

                            AddOption(Modifier.padding(start = 5.dp, end = 5.dp), {
                                Button({
                                    chosenBlock = 0
                                }, colors = getButtonColor()) { Text(stringResource(R.string.cancel)) }
                            }) {
                                Button({
                                    when(chosenBlock) {
                                        1 -> first = color
                                        2 -> second = color
                                        3 -> third = color
                                        4 -> fourth = color
                                        5 -> fifth = color
                                        6 -> sixth = color
                                    }
                                    chosenBlock = 0
                                    changeCheck.invoke()
                                }, colors = getButtonColor()) { Text(stringResource(R.string.save)) }
                            }
                        }
                    }
                }
            }

            this.item {
                Spacer(Modifier.size(SPACER))
            }

            this.item {
                Column(Modifier.fillMaxWidth(1F)) {
                    Column(Modifier.align(Alignment.CenterHorizontally)) {
                        AddOption(Modifier.padding(start = 5.dp, end = 5.dp), {
                            Button(modifier = Modifier.width(200.dp), colors = getButtonColor(), onClick = {
                                moveBlocksWithClick = DEFAULT_SETTINGS.moveBlocksWithClick
                                multiMoveBlocksWithClick = DEFAULT_SETTINGS.multiMoveWithCLick
                                addNumberBorder = DEFAULT_SETTINGS.addNumberBorder
                                switchNumbersColor = DEFAULT_SETTINGS.switchNumbersColor
                                first = Color(DEFAULT_SETTINGS.firstRed, DEFAULT_SETTINGS.firstGreen, DEFAULT_SETTINGS.firstBlue)
                                second = Color(DEFAULT_SETTINGS.secondRed, DEFAULT_SETTINGS.secondGreen, DEFAULT_SETTINGS.secondBlue)
                                third = Color(DEFAULT_SETTINGS.thirdRed, DEFAULT_SETTINGS.thirdGreen, DEFAULT_SETTINGS.thirdBlue)
                                fourth = Color(DEFAULT_SETTINGS.fourthRed, DEFAULT_SETTINGS.fourthGreen, DEFAULT_SETTINGS.fourthBlue)
                                fifth = Color(DEFAULT_SETTINGS.fifthRed, DEFAULT_SETTINGS.fifthGreen, DEFAULT_SETTINGS.fifthBlue)
                                sixth = Color(DEFAULT_SETTINGS.sixthRed, DEFAULT_SETTINGS.sixthGreen, DEFAULT_SETTINGS.sixthBlue)

                                changeCheck.invoke()
                            }) {
                                AddButtonText(text = stringResource(R.string.button_default))
                            }
                        }) {
                            Button(modifier = Modifier.width(200.dp), colors = getButtonColor(), enabled = sthChanged, onClick = {
                                saveSettings(activity, Settings(
                                    0,
                                    moveBlocksWithClick,
                                    multiMoveBlocksWithClick, switchNumbersColor, addNumberBorder,
                                    first.red, first.green, first.blue,
                                    second.red, second.green, second.blue,
                                    third.red, third.green, third.blue,
                                    fourth.red, fourth.green, fourth.blue,
                                    fifth.red, fifth.green, fifth.blue,
                                    sixth.red, sixth.green, sixth.blue
                                ))

                                sthChanged = false
                            }) {
                                AddButtonText(text = stringResource(R.string.button_save))
                            }
                        }
                    }
                }

                Spacer(Modifier.size(SMALL_SPACER))
            }
        }
    }

    if(shouldShowWarning){
        AlertDialog(onDismissRequest = {
            shouldShowWarning = false
        }, confirmButton = {
            TextButton(onClick = {
                shouldShowWarning = false
                saveSettings(activity, Settings(
                    0,
                    moveBlocksWithClick,
                    multiMoveBlocksWithClick, switchNumbersColor, addNumberBorder,
                    first.red, first.green, first.blue,
                    second.red, second.green, second.blue,
                    third.red, third.green, third.blue,
                    fourth.red, fourth.green, fourth.blue,
                    fifth.red, fifth.green, fifth.blue,
                    sixth.red, sixth.green, sixth.blue
                ))
                activity.onBackPressed()
            }) {
                Text(text = "Tak")
            }
        }, text = {
            Text(text = stringResource(R.string.settings_warning))
        }, dismissButton = {
            TextButton(onClick = {
                shouldShowWarning = false
                activity.onBackPressed()
            }) {
                Text(text = "Nie")
            }
        })
    }
}

@Composable
private fun CreateColorSlider(value : Float, onValueChanged : (Float) -> Unit) {
    Slider(
        modifier = Modifier.width(100.dp).padding(end = 7.dp),
        value = value,
        valueRange = 0f..1f,
        steps = 101,
        colors = SliderDefaults.colors(
            thumbColor = if(isSystemInDarkTheme()) Guziki else GuzikiJasne,
            activeTickColor = if(isSystemInDarkTheme()) Guziki else GuzikiJasne,
            activeTrackColor = PurpleGrey80,
            inactiveTrackColor = Color.Transparent
        ),
        onValueChange = {
            onValueChanged.invoke(it)
        })
}

@Composable
private fun CreateColorBlock(color : Color, chosen : Boolean, addNumberBorder : Boolean = false, switchNumbersColor : Boolean = false, onClick : () -> Unit) {
    Box(modifier = Modifier
        .background(color)
        .width(50.dp)
        .height(50.dp)
        .border(5.dp, if(chosen) Color.White else Color.Transparent)
        .clickable(onClick = {
            onClick.invoke()
        })) {
        val number = Random.nextInt(1, 100).toString()
        if(addNumberBorder){
            Text(
                text = number,
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(fontSize = 20.sp, color = getColorForNumbers(switchNumbersColor), drawStyle = Stroke(12f), letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
            )
        }

        Text(
            text = number,
            modifier = Modifier.align(Alignment.Center),
            fontWeight = FontWeight.Black,
            fontSize = 20.sp,
            color = getColorForNumbers(!switchNumbersColor)
        )
    }
}

private fun saveSettings(activity : MainActivity, newSettings : Settings) {
    activity.viewModel?.viewModelScope?.launch {
        activity.container?.settingsRepository?.saveOptions(newSettings)

    }.also {
        activity.updateSettings()
    }
}

private fun nothingChanged(
    settings : Settings,
    moveWithClick : Boolean = settings.moveBlocksWithClick,
    multiMoveWitchClick : Boolean = settings.multiMoveWithCLick,
    switchNumbersColor : Boolean = settings.switchNumbersColor,
    addNumberBorder : Boolean = settings.addNumberBorder,
    first : Color = Color(settings.firstRed, settings.firstGreen, settings.firstBlue),
    second : Color = Color(settings.secondRed, settings.secondGreen, settings.secondBlue),
    third : Color = Color(settings.thirdRed, settings.thirdGreen, settings.thirdBlue),
    fourth : Color = Color(settings.fourthRed, settings.fourthGreen, settings.fourthBlue),
    fifth : Color = Color(settings.fifthRed, settings.fifthGreen, settings.fifthBlue),
    sixth : Color = Color(settings.sixthRed, settings.sixthGreen, settings.sixthBlue),
) : Boolean {

    return settings.moveBlocksWithClick == moveWithClick &&
            settings.multiMoveWithCLick == multiMoveWitchClick &&
            settings.switchNumbersColor == switchNumbersColor &&
            settings.addNumberBorder == addNumberBorder &&
            settings.firstRed == first.red &&
            settings.firstGreen == first.green &&
            settings.firstBlue == first.blue &&
            settings.secondRed == second.red &&
            settings.secondGreen == second.green &&
            settings.secondBlue == second.blue &&
            settings.thirdRed == third.red &&
            settings.thirdGreen == third.green &&
            settings.thirdBlue == third.blue &&
            settings.fourthRed == fourth.red &&
            settings.fourthGreen == fourth.green &&
            settings.fourthBlue == fourth.blue &&
            settings.fifthRed == fifth.red &&
            settings.fifthGreen == fifth.green &&
            settings.fifthBlue == fifth.blue &&
            settings.sixthRed == sixth.red &&
            settings.sixthGreen == sixth.green &&
            settings.sixthBlue == sixth.blue
}