package net.fryc.gra

import androidx.compose.ui.graphics.Color
import net.fryc.gra.logic.Board
import net.fryc.gra.logic.Difficulty
import net.fryc.gra.logic.Field
import net.fryc.gra.storage.settings.Settings
import net.fryc.gra.ui.screen.DEFAULT_SETTINGS
import net.fryc.gra.ui.screen.getCorrectlyAlignedFieldsByColumn
import net.fryc.gra.ui.screen.getCorrectlyAlignedFieldsByRow
import org.junit.Assert
import org.junit.Test


class GameLogicTest {

    private val settings : Settings = DEFAULT_SETTINGS

    @Test
    fun when_rowsOrColumnsAreCorrectlyOrdered_then_PlayerWins() {
        val board = Board(4, Difficulty.EASY, this.settings)
        val normalBoard = Board(4, Difficulty.NORMAL, this.settings)
        val wrongRowsCorrectColumns = getCorrectlyAlignedFieldsByColumn(board)
        val wrongColumnsCorrectRows = getCorrectlyAlignedFieldsByRow(board)

        board.fields.clear()
        board.fields.addAll(wrongRowsCorrectColumns)
        board.fieldsMatrix.clear()
        board.fieldsMatrix.addAll(board.createFieldsMatrix())

        Assert.assertTrue(board.checkColumnWin())

        board.fields.clear()
        board.fields.addAll(wrongColumnsCorrectRows)
        board.fieldsMatrix.clear()
        board.fieldsMatrix.addAll(board.createFieldsMatrix())

        Assert.assertTrue(board.checkRowWin())

        wrongColumnsCorrectRows.replaceAll {
            return@replaceAll Field(it.y, it.x, it.color, it.x + 1, it.board)
        }

        wrongRowsCorrectColumns.replaceAll {
            return@replaceAll Field(it.y, it.x, it.color, it.y + 1, it.board)
        }

        normalBoard.fields.clear()
        normalBoard.fields.addAll(wrongRowsCorrectColumns)
        normalBoard.fieldsMatrix.clear()
        normalBoard.fieldsMatrix.addAll(normalBoard.createFieldsMatrix())

        Assert.assertTrue(normalBoard.checkColumnWin())

        normalBoard.fields.clear()
        normalBoard.fields.addAll(wrongColumnsCorrectRows)
        normalBoard.fieldsMatrix.clear()
        normalBoard.fieldsMatrix.addAll(normalBoard.createFieldsMatrix())

        Assert.assertTrue(normalBoard.checkRowWin())
    }

    @Test
    fun when_boardIsCreated_then_numberOfIdenticalFieldsIsAppropriate() {
        val BOARDS_TO_TEST_PER_DIFFICULTY_ABOVE_EASY = 25

        Difficulty.entries.forEach {
            if(it.ordinal > 0) {
                var i = 0
                while(i < BOARDS_TO_TEST_PER_DIFFICULTY_ABOVE_EASY) {
                    Assert.assertTrue(hasRequiredNumberOfIdenticalFields(Board(4, it, settings)))
                    Assert.assertTrue(hasRequiredNumberOfIdenticalFields(Board(5, it, settings)))
                    Assert.assertTrue(hasRequiredNumberOfIdenticalFields(Board(6, it, settings)))

                    i++
                }
            }
        }
    }

    fun hasRequiredNumberOfIdenticalFields(board : Board) : Boolean {
        val map = HashMap<Color, HashMap<Int, Int>>()
        board.fields.forEach {
            map.getOrPut(it.color) { HashMap() }.let { map2 ->
                map2.put(
                    it.value,
                    map2.getOrPut(it.value) { -1 }.plus(1)
                )
            }
        }

        return map.values.sumOf { it.values.sum() } == board.difficulty.getSameNumbersAmount()
    }
}