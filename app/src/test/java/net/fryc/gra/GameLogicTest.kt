package net.fryc.gra

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
}