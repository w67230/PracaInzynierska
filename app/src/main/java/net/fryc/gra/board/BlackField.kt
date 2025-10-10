package net.fryc.gra.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.fryc.gra.MainActivity

class BlackField(override var y: Int, override var x : Int, override val value : Int, override val board: Board) : Field(y, x, Color.Unspecified, value, board) {

    @Composable
    override fun drawBox(activity: MainActivity){
        Box(Modifier.height((90-this.board.size*5).dp).width((90-this.board.size*5).dp).padding(5.dp, 5.dp).background(this.color));
    }

    override fun tryToMove(direction : Direction, activity: MainActivity, multiMove : Boolean) {
    }

    override fun tryToMultiMove(direction : Direction, activity: MainActivity) {
    }

    override fun canMoveHorizontally(left : Boolean, field: Field) : Boolean {
        return false;
    }

    override fun canMoveVertically(up : Boolean, field: Field) : Boolean {
        return false;
    }

    override fun canMove() : Boolean {
        return false;
    }

    override fun move(field: Field){
    }

}