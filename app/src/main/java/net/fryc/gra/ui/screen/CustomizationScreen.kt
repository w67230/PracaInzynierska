package net.fryc.gra.ui.screen

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.storage.customization.Customization
import net.fryc.gra.ui.theme.GraTheme
import net.fryc.gra.ui.theme.OPTION_FONT_SIZE
import net.fryc.gra.ui.theme.SMALLER_OPTION_FONT_SIZE


val DEFAULT_CUSTOMIZATION = Customization(0, 0, 4, true, true)
val AVAILABLE_DIFFICULTIES = listOf<Int>(0, 1, 2, 3)
val AVAILABLE_SIZES = listOf<Int>(4, 5, 6)

fun customization(activity: MainActivity){
    activity.setContent {
        GraTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                CustomizationScreen(activity)
            }
        }
    }
}

@Composable
fun CustomizationScreen(activity: MainActivity) {
    val fontSize = getAppropriateSize(SMALLER_OPTION_FONT_SIZE, OPTION_FONT_SIZE)
    var size by remember { mutableIntStateOf(activity.lastCustomization.size) }
    var difficulty by remember { mutableStateOf(getDifficultyFromInt(activity.lastCustomization.difficulty)) }
    var showTimer by remember { mutableStateOf(activity.lastCustomization.showTimer) }
    var showMoves by remember { mutableStateOf(activity.lastCustomization.showMoves) }

    var sizeExpanded by remember { mutableStateOf(false) }
    var diffExpanded by remember { mutableStateOf(false) }

    Column {

        AddNavigationBar(Modifier.background(Color.Red).align(Alignment.Start), {
            activity.onBackPressed()
        }, false) { }

        AddOption(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = PADDING_TOP_BELOW_NAV_BAR), description = {
            Text(text = stringResource(R.string.size_info), fontSize = fontSize, modifier = Modifier
                .padding(end = 20.dp, start = 5.dp))
        }) {
            Row(modifier = Modifier.padding(start = 20.dp, end = 5.dp).clickable {
                sizeExpanded = true
            }) {
                Text(text = getSizeName(size), fontSize = fontSize, modifier = Modifier.padding(end = 10.dp))

                Icon(
                    painter = getDropdownIcon(diffExpanded),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(32.dp)
                )

                DropdownMenu(expanded = sizeExpanded, onDismissRequest = {
                    sizeExpanded = false;
                }) {
                    AVAILABLE_SIZES.forEach {
                        DropdownMenuItem(text = {Text(getSizeName(it))}, onClick = {
                            size = it
                            sizeExpanded = false
                        })
                    }
                }
            }
        }

        AddOption(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp), description = {
            Text(text = stringResource(R.string.difficulty), fontSize = fontSize, modifier = Modifier
                .padding(end = 20.dp, start = 5.dp))
        }) {
            Row(modifier = Modifier.padding(start = 20.dp, end = 5.dp).clickable {
                diffExpanded = true
            }) {
                Text(text = getDifficultyName(difficulty), fontSize = fontSize, modifier = Modifier.padding(end = 10.dp))

                Icon(
                    painter = getDropdownIcon(diffExpanded),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(30.dp)
                )

                DropdownMenu(expanded = diffExpanded, onDismissRequest = {
                    diffExpanded = false;
                }) {
                    AVAILABLE_DIFFICULTIES.forEach {
                        DropdownMenuItem(text = {Text(getDifficultyName(getDifficultyFromInt(it)))}, onClick = {
                            difficulty = getDifficultyFromInt(it)
                            diffExpanded = false
                        })
                    }
                }
            }
        }

        AddOption(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp), description = {
            Text(stringResource(R.string.timer), fontSize = fontSize, modifier = Modifier
                .padding(end = 20.dp, start = 5.dp))
        }) {
            Switch(modifier = Modifier.padding(start = 20.dp, end = 5.dp), checked = showTimer, onCheckedChange = {
                showTimer = !showTimer
            })
        }

        AddOption(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp), description = {
            Text(stringResource(R.string.moveCounter), fontSize = fontSize, modifier = Modifier
                .padding(end = 20.dp, start = 5.dp))
        }) {
            Switch(modifier = Modifier.padding(start = 20.dp, end = 5.dp), checked = showMoves, onCheckedChange = {
                showMoves = !showMoves
            })
        }

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 30.dp)) {
            AddButton(onClick = {
                activity.backStack.add {
                    customization(it)
                }
                saveCustomization(activity, Customization(0, difficulty.ordinal, size, showTimer, showMoves))
                startGame(size, difficulty, showTimer, showMoves, activity)
            }) {
                AddButtonText(text = stringResource(R.string.start))
            }
        }
    }
}

private fun saveCustomization(activity : MainActivity, newCustomization : Customization) {
    activity.viewModel?.viewModelScope?.launch {
        activity.container?.customizationRepository?.saveCustomization(newCustomization)

    }.also {
        activity.updateLastCustomization()
    }
}