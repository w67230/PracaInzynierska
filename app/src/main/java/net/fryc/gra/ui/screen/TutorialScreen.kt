package net.fryc.gra.ui.screen

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.logic.BlackField
import net.fryc.gra.logic.Board
import net.fryc.gra.logic.Difficulty
import net.fryc.gra.logic.Field
import net.fryc.gra.storage.settings.Settings
import net.fryc.gra.ui.theme.GraTheme
import net.fryc.gra.ui.theme.getButtonColor


fun howToPlay(activity: MainActivity, difficulty : Difficulty = Difficulty.EASY){
    val board = Board(4, difficulty, activity.settings, false, false)
    board.fields.clear()
    board.fields.addAll(
        if(difficulty == Difficulty.EASY)
            getCorrectlyAlignedFieldsByRow(board, activity.settings)
        else
            getCorrectlyAlignedFieldsByColumn(board, activity.settings)
    )
    board.fieldsMatrix.clear()
    board.fieldsMatrix.addAll(board.createFieldsMatrix())
    board.uiUpdateFun = { acti, boar ->
        howToPlay(acti, boar)
    }

    activity.setContent {
        GraTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                howToPlayScreen(activity = activity, board = board)
            }
        }
    }
}

fun howToPlay(activity: MainActivity, board : Board){
    activity.setContent {
        GraTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                howToPlayScreen(activity = activity, board = board)
            }
        }
    }
}

@Composable
fun howToPlayScreen(activity: MainActivity, board : Board){
    Column {
        AddNavigationBar(Modifier.background(Color.Red).align(Alignment.Start), {
            activity.onBackPressed()
        }, false) { }

        LazyColumn(modifier = Modifier.padding(start = 30.dp, end = 20.dp).fillMaxWidth(1F)) {
            this.item {
                Spacer(Modifier.size(PADDING_TOP_BELOW_NAV_BAR))
            }
            this.item {
                Column(Modifier.fillMaxWidth(1F)) {
                    AddOption(Modifier.padding(start = 5.dp, end = 5.dp).fillMaxWidth(1F), {
                        Button(modifier = Modifier.width(200.dp).padding(end = 5.dp), colors = getButtonColor(), enabled = board.difficulty != Difficulty.EASY, onClick = {
                            howToPlay(activity, Difficulty.EASY)
                        }) {
                            AddButtonText(text = stringResource(R.string.easy))
                        }
                    }) {
                        Button(modifier = Modifier.width(200.dp).padding(start = 5.dp), colors = getButtonColor(), enabled = board.difficulty == Difficulty.EASY, onClick = {
                            howToPlay(activity, Difficulty.NORMAL)
                        }) {
                            AddButtonText(text = stringResource(R.string.normal))
                        }
                    }
                }
            }
            this.item {
                Spacer(Modifier.size(SPACER))
            }
            this.item {
                Column(Modifier.fillMaxWidth(1F)) {
                    Row(Modifier.align(Alignment.CenterHorizontally)) {
                        if(board.difficulty == Difficulty.EASY) {
                            ShowSimpleText(resourceString = R.string.cel_gry)
                        }
                        else {
                            ShowSimpleText(resourceString = R.string.cel_dodatkowo)
                        }
                    }
                }
            }
            this.item {
                Spacer(Modifier.size(SMALL_SPACER))
            }
            this.item {
                Column(Modifier.fillMaxWidth(1F)) {
                    Row(Modifier.align(Alignment.CenterHorizontally)) {
                        if(board.checkWin()) {
                            ShowSimpleText(resourceString = R.string.przyklad)
                        }
                        else {
                            Text(
                                text = stringResource(R.string.przyklad_zly),
                                modifier = Modifier.padding(bottom = 10.dp),
                                color = Color.Red
                            )
                        }
                    }
                }
            }
            this.item {
                Spacer(Modifier.size(SMALL_SPACER))
            }
            this.item {
                Column(Modifier.fillMaxWidth(1F)) {
                    board.fieldsMatrix.forEach {
                        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            it.forEach { field ->
                                field.DrawBox(activity)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getCorrectlyAlignedFieldsByRow(board : Board, settings: Settings) : ArrayList<Field> {
    val wrongColumnsCorrectRows = ArrayList<Field>()
    val firstColor = Color(settings.firstRed, settings.firstGreen, settings.firstBlue)
    val secondColor = Color(settings.secondRed, settings.secondGreen, settings.secondBlue)
    val thirdColor = Color(settings.thirdRed, settings.thirdGreen, settings.thirdBlue)
    val fourthColor = Color(settings.fourthRed, settings.fourthGreen, settings.fourthBlue)

    wrongColumnsCorrectRows.add(Field(0, 0, firstColor, getFieldNumber(board.difficulty, 1), board))
    wrongColumnsCorrectRows.add(Field(0, 1, firstColor, getFieldNumber(board.difficulty, 2), board))
    wrongColumnsCorrectRows.add(Field(0, 2, firstColor, getFieldNumber(board.difficulty, 3), board))
    wrongColumnsCorrectRows.add(Field(0, 3, firstColor, getFieldNumber(board.difficulty, 4), board))

    wrongColumnsCorrectRows.add(Field(1, 0, secondColor, getFieldNumber(board.difficulty, 2), board))
    wrongColumnsCorrectRows.add(Field(1, 1, secondColor, getFieldNumber(board.difficulty, 3), board))
    wrongColumnsCorrectRows.add(Field(1, 2, secondColor, getFieldNumber(board.difficulty, 4), board))
    wrongColumnsCorrectRows.add(Field(1, 3, secondColor, getFieldNumber(board.difficulty, 15), board))

    wrongColumnsCorrectRows.add(Field(2, 0, thirdColor, getFieldNumber(board.difficulty, 1), board))
    wrongColumnsCorrectRows.add(Field(2, 1, thirdColor, getFieldNumber(board.difficulty, 3), board))
    wrongColumnsCorrectRows.add(Field(2, 2, thirdColor, getFieldNumber(board.difficulty, 6), board))
    wrongColumnsCorrectRows.add(Field(2, 3, thirdColor, getFieldNumber(board.difficulty, 8), board))

    wrongColumnsCorrectRows.add(Field(3, 0, fourthColor, getFieldNumber(board.difficulty, 12), board))
    wrongColumnsCorrectRows.add(BlackField(3, 1, -1, board))
    wrongColumnsCorrectRows.add(Field(3, 2, fourthColor, getFieldNumber(board.difficulty, 28), board))
    wrongColumnsCorrectRows.add(Field(3, 3, fourthColor, getFieldNumber(board.difficulty, 59), board))

    return wrongColumnsCorrectRows;
}

fun getCorrectlyAlignedFieldsByColumn(board : Board, settings : Settings) : ArrayList<Field> {
    val wrongRowsCorrectColumns = ArrayList<Field>()
    val firstColor = Color(settings.firstRed, settings.firstGreen, settings.firstBlue)
    val secondColor = Color(settings.secondRed, settings.secondGreen, settings.secondBlue)
    val thirdColor = Color(settings.thirdRed, settings.thirdGreen, settings.thirdBlue)
    val fourthColor = Color(settings.fourthRed, settings.fourthGreen, settings.fourthBlue)

    wrongRowsCorrectColumns.add(Field(0, 0, firstColor, getFieldNumber(board.difficulty, 1), board))
    wrongRowsCorrectColumns.add(Field(1, 0, firstColor, getFieldNumber(board.difficulty, 2), board))
    wrongRowsCorrectColumns.add(Field(2, 0, firstColor, getFieldNumber(board.difficulty, 3), board))
    wrongRowsCorrectColumns.add(Field(3, 0, firstColor, getFieldNumber(board.difficulty, 4), board))

    wrongRowsCorrectColumns.add(Field(0, 1, secondColor, getFieldNumber(board.difficulty, 2), board))
    wrongRowsCorrectColumns.add(Field(1, 1, secondColor, getFieldNumber(board.difficulty, 4), board))
    wrongRowsCorrectColumns.add(Field(2, 1, secondColor, getFieldNumber(board.difficulty, 4), board))
    wrongRowsCorrectColumns.add(Field(3, 1, secondColor, getFieldNumber(board.difficulty, 15), board))

    wrongRowsCorrectColumns.add(Field(0, 2, thirdColor, getFieldNumber(board.difficulty, 1), board))
    wrongRowsCorrectColumns.add(Field(1, 2, thirdColor, getFieldNumber(board.difficulty, 3), board))
    wrongRowsCorrectColumns.add(Field(2, 2, thirdColor, getFieldNumber(board.difficulty, 6), board))
    wrongRowsCorrectColumns.add(Field(3, 2, thirdColor, getFieldNumber(board.difficulty, 8), board))

    wrongRowsCorrectColumns.add(Field(0, 3, fourthColor, getFieldNumber(board.difficulty, 12), board))
    wrongRowsCorrectColumns.add(BlackField(1, 3, -1, board))
    wrongRowsCorrectColumns.add(Field(2, 3, fourthColor, getFieldNumber(board.difficulty, 28), board))
    wrongRowsCorrectColumns.add(Field(3, 3, fourthColor, getFieldNumber(board.difficulty, 59), board))

    return wrongRowsCorrectColumns;
}

fun getFieldNumber(difficulty : Difficulty, number : Int) : Int {
    return if(difficulty == Difficulty.EASY) 0 else number
}