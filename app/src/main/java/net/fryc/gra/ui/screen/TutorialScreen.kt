package net.fryc.gra.ui.screen

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.logic.BlackField
import net.fryc.gra.logic.Board
import net.fryc.gra.logic.Difficulty
import net.fryc.gra.logic.Field
import net.fryc.gra.ui.theme.GraTheme


fun howToPlay(activity: MainActivity){
    val board = Board(4, Difficulty.EASY, activity.settings, false, false)
    board.fields.clear()
    board.fields.addAll(getCorrectlyAlignedFieldsByRow(board))
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
    var x : Int
    var y : Int
    Column {

        AddNavigationBar(Modifier.background(Color.Red).align(Alignment.Start), {
            activity.onBackPressed()
        }, false) { }

        LazyColumn(modifier = Modifier.padding(start = 30.dp, end = 20.dp)) {
            this.item {
                Spacer(Modifier.size(PADDING_TOP_BELOW_NAV_BAR))
            }
            this.item {
                Text(text = stringResource(R.string.tytul_cel), fontWeight = FontWeight.Bold, fontSize = 6.em, modifier = Modifier.padding(bottom = 10.dp))
            }
            this.item {
                ShowSimpleText(resourceString = R.string.cel_gry)
            }
            this.item {
                ShowSimpleText(resourceString = R.string.przyklad_latwy)
            }
            this.item {
                board.fieldsMatrix.forEach {
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        it.forEach { field ->
                            field.DrawBox(activity)
                        }
                    }
                }
            }
            /*
            this.item {
                x=0
                y=0
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
                                            0 -> Color.Green
                                            1 -> Color.Red
                                            2 -> Color.Cyan
                                            3 -> if (x == 3) Color.Transparent else Color.Magenta
                                            else -> Color.Transparent
                                        }
                                    ))

                            x++
                        }
                    }

                    x = 0
                    y++
                }
            }

             */
            this.item {
                ShowSimpleText(resourceString = R.string.cel_dodatkowo)
            }
            this.item {
                ShowSimpleText(resourceString = R.string.hard_cel_przyklad)
            }
            this.item {
                x=0
                y=0
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
                                            0 -> Color.Cyan
                                            1 -> Color.Red
                                            2 -> Color.Green
                                            3 -> if (x == 3) Color.Transparent else Color.Magenta
                                            else -> Color.Transparent
                                        }
                                    )){
                                if(y < 3 || x < 3){
                                    Text(text = x.toString(), modifier = Modifier.align(Alignment.Center), color = Color.Black, fontWeight = FontWeight.Bold)
                                }
                            }

                            x++
                        }
                    }

                    x = 0
                    y++
                }
            }
            this.item {
                Button(onClick = {
                    activity.onBackPressed()
                }) {
                    Text(text = "Ok")
                }
            }
        }
    }
}

fun getCorrectlyAlignedFieldsByRow(board : Board) : ArrayList<Field> {
    val wrongColumnsCorrectRows = ArrayList<Field>()

    wrongColumnsCorrectRows.add(Field(0, 0, Color.Red, 0, board))
    wrongColumnsCorrectRows.add(Field(0, 1, Color.Red, 0, board))
    wrongColumnsCorrectRows.add(Field(0, 2, Color.Red, 0, board))
    wrongColumnsCorrectRows.add(Field(0, 3, Color.Red, 0, board))

    wrongColumnsCorrectRows.add(Field(1, 0, Color.Green, 0, board))
    wrongColumnsCorrectRows.add(Field(1, 1, Color.Green, 0, board))
    wrongColumnsCorrectRows.add(Field(1, 2, Color.Green, 0, board))
    wrongColumnsCorrectRows.add(Field(1, 3, Color.Green, 0, board))

    wrongColumnsCorrectRows.add(Field(2, 0, Color.Blue, 0, board))
    wrongColumnsCorrectRows.add(Field(2, 1, Color.Blue, 0, board))
    wrongColumnsCorrectRows.add(Field(2, 2, Color.Blue, 0, board))
    wrongColumnsCorrectRows.add(Field(2, 3, Color.Blue, 0, board))

    wrongColumnsCorrectRows.add(Field(3, 0, Color.Magenta, 0, board))
    wrongColumnsCorrectRows.add(BlackField(3, 1, 0, board))
    wrongColumnsCorrectRows.add(Field(3, 2, Color.Magenta, 0, board))
    wrongColumnsCorrectRows.add(Field(3, 3, Color.Magenta, 0, board))

    return wrongColumnsCorrectRows;
}

fun getCorrectlyAlignedFieldsByColumn(board : Board) : ArrayList<Field> {
    val wrongRowsCorrectColumns = ArrayList<Field>()

    wrongRowsCorrectColumns.add(Field(0, 0, Color.Red, 0, board))
    wrongRowsCorrectColumns.add(Field(1, 0, Color.Red, 0, board))
    wrongRowsCorrectColumns.add(Field(2, 0, Color.Red, 0, board))
    wrongRowsCorrectColumns.add(Field(3, 0, Color.Red, 0, board))

    wrongRowsCorrectColumns.add(Field(0, 1, Color.Green, 0, board))
    wrongRowsCorrectColumns.add(Field(1, 1, Color.Green, 0, board))
    wrongRowsCorrectColumns.add(Field(2, 1, Color.Green, 0, board))
    wrongRowsCorrectColumns.add(Field(3, 1, Color.Green, 0, board))

    wrongRowsCorrectColumns.add(Field(0, 2, Color.Blue, 0, board))
    wrongRowsCorrectColumns.add(Field(1, 2, Color.Blue, 0, board))
    wrongRowsCorrectColumns.add(Field(2, 2, Color.Blue, 0, board))
    wrongRowsCorrectColumns.add(Field(3, 2, Color.Blue, 0, board))

    wrongRowsCorrectColumns.add(Field(0, 3, Color.Magenta, 0, board))
    wrongRowsCorrectColumns.add(BlackField(1, 3, 0, board))
    wrongRowsCorrectColumns.add(Field(2, 3, Color.Magenta, 0, board))
    wrongRowsCorrectColumns.add(Field(3, 3, Color.Magenta, 0, board))

    return wrongRowsCorrectColumns;
}