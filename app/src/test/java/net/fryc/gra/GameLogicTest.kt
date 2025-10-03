package net.fryc.gra

import androidx.compose.ui.graphics.Color
import net.fryc.gra.board.Board
import net.fryc.gra.board.Difficulty
import net.fryc.gra.board.Field
import org.junit.Assert
import org.junit.Test


class GameLogicTest {

    @Test
    fun when_rowsOrColumnsAreCorrectlyOrdered_then_PlayerWins() {
        val board = Board(4, Difficulty.EASY);
        val normalBoard = Board(4, Difficulty.NORMAL);
        val wrongRowsCorrectColumns = ArrayList<Field>();
        val wrongColumnsCorrectRows = ArrayList<Field>();

        wrongRowsCorrectColumns.add(Field(0, 0, Color.Red, 0, board));
        wrongRowsCorrectColumns.add(Field(1, 0, Color.Red, 0, board));
        wrongRowsCorrectColumns.add(Field(2, 0, Color.Red, 0, board));
        wrongRowsCorrectColumns.add(Field(3, 0, Color.Red, 0, board));

        wrongRowsCorrectColumns.add(Field(0, 1, Color.Green, 0, board));
        wrongRowsCorrectColumns.add(Field(1, 1, Color.Green, 0, board));
        wrongRowsCorrectColumns.add(Field(2, 1, Color.Green, 0, board));
        wrongRowsCorrectColumns.add(Field(3, 1, Color.Green, 0, board));

        wrongRowsCorrectColumns.add(Field(0, 2, Color.Blue, 0, board));
        wrongRowsCorrectColumns.add(Field(1, 2, Color.Blue, 0, board));
        wrongRowsCorrectColumns.add(Field(2, 2, Color.Blue, 0, board));
        wrongRowsCorrectColumns.add(Field(3, 2, Color.Blue, 0, board));

        wrongRowsCorrectColumns.add(Field(0, 3, Color.Magenta, 0, board));
        wrongRowsCorrectColumns.add(Field(1, 3, Color.Unspecified, 0, board));
        wrongRowsCorrectColumns.add(Field(2, 3, Color.Magenta, 0, board));
        wrongRowsCorrectColumns.add(Field(3, 3, Color.Magenta, 0, board));

        //-----

        wrongColumnsCorrectRows.add(Field(0, 0, Color.Red, 0, board));
        wrongColumnsCorrectRows.add(Field(0, 1, Color.Red, 0, board));
        wrongColumnsCorrectRows.add(Field(0, 2, Color.Red, 0, board));
        wrongColumnsCorrectRows.add(Field(0, 3, Color.Red, 0, board));

        wrongColumnsCorrectRows.add(Field(1, 0, Color.Green, 0, board));
        wrongColumnsCorrectRows.add(Field(1, 1, Color.Green, 0, board));
        wrongColumnsCorrectRows.add(Field(1, 2, Color.Green, 0, board));
        wrongColumnsCorrectRows.add(Field(1, 3, Color.Green, 0, board));

        wrongColumnsCorrectRows.add(Field(2, 0, Color.Blue, 0, board));
        wrongColumnsCorrectRows.add(Field(2, 1, Color.Blue, 0, board));
        wrongColumnsCorrectRows.add(Field(2, 2, Color.Blue, 0, board));
        wrongColumnsCorrectRows.add(Field(2, 3, Color.Blue, 0, board));

        wrongColumnsCorrectRows.add(Field(3, 0, Color.Magenta, 0, board));
        wrongColumnsCorrectRows.add(Field(3, 1, Color.Unspecified, 0, board));
        wrongColumnsCorrectRows.add(Field(3, 2, Color.Magenta, 0, board));
        wrongColumnsCorrectRows.add(Field(3, 3, Color.Magenta, 0, board));


        board.fields.clear();
        board.fields.addAll(wrongRowsCorrectColumns);

        Assert.assertTrue(board.checkColumnWin());

        board.fields.clear();
        board.fields.addAll(wrongColumnsCorrectRows);

        Assert.assertTrue(board.checkRowWin());

        wrongColumnsCorrectRows.replaceAll {
            return@replaceAll Field(it.y, it.x, it.color, it.x + 1, it.board);
        }

        wrongRowsCorrectColumns.replaceAll {
            return@replaceAll Field(it.y, it.x, it.color, it.y + 1, it.board);
        }

        normalBoard.fields.clear();
        normalBoard.fields.addAll(wrongRowsCorrectColumns);

        Assert.assertTrue(normalBoard.checkColumnWin());

        normalBoard.fields.clear();
        normalBoard.fields.addAll(wrongColumnsCorrectRows);

        Assert.assertTrue(normalBoard.checkRowWin());
    }
}