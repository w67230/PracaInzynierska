package net.fryc.gra.logic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.fryc.gra.MainActivity
import net.fryc.gra.ui.screen.redraw
import kotlin.math.abs


open class Field(open var y: Int, open var x : Int, open val color : Color, open val value : Int, open val board: Board) {

    @Composable
    open fun drawBox(activity: MainActivity){
        Box(Modifier.height((90-this.board.size*5).dp).width((90-this.board.size*5).dp).padding(5.dp, 5.dp).background(this.color).clickable {
            if(activity.settings.moveBlocksWithClick){
                if(this.canMove()){
                    this.move();
                    this.board.increaseMovesCount();
                    redraw(this.board, activity);
                }
            }
        }.draggable(rememberDraggableState {
            if(it != 0F){
                if(this.tryToMove(if(it < 0) Direction.LEFT else Direction.RIGHT, activity)){
                    this.board.increaseMovesCount();
                }
            }
        }, Orientation.Horizontal).draggable(rememberDraggableState {
            if(it != 0F){
                if(this.tryToMove(if(it < 0) Direction.UP else Direction.DOWN, activity)){
                    this.board.increaseMovesCount();
                }
            }
        }, Orientation.Vertical)) {
            if(this@Field.value > 0 && this@Field.board.difficulty > Difficulty.EASY){
                /* TODO obramowka do opcji
                Text(
                    text = this@Field.value.toString(),
                    modifier = Modifier.align(Alignment.Center),
                    style = TextStyle(fontSize = 20.sp, color = Color.White, drawStyle = Stroke(12f), letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
                );

                 */
                Text(
                    text = this@Field.value.toString(),
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    color = Color.Black
                );
            }
        }
    }

    open fun tryToMove(direction : Direction, activity: MainActivity, multiMove : Boolean = true) : Boolean {
        val blackField = this.board.getBlackField();
        if(this.isOnSameLine(blackField)){
            if(direction.isDirectionalMovingPossible(this, blackField)){
                this.move(blackField);
                redraw(this.board, activity);

                return true;
            }
            else if(multiMove){
                this.tryToMultiMove(direction, activity);
                return this.tryToMove(direction, activity, false);
            }
        }

        return false;
    }

    open fun tryToMultiMove(direction : Direction, activity: MainActivity) {
        direction.getNextField(this)?.tryToMove(direction, activity);
    }

    open fun canMoveHorizontally(left : Boolean, field: Field = this.board.getBlackField()) : Boolean {
        return this.isNextToField(field) && ( (field.x-this.x > 0 && !left) || (field.x-this.x < 0 && left) );
    }

    open fun canMoveVertically(up : Boolean, field: Field = this.board.getBlackField()) : Boolean {
        return this.isNextToField(field) && ( (field.y-this.y > 0 && !up) || (field.y-this.y < 0 && up) );
    }

    open fun canMove() : Boolean {
        return this.canMoveVertically(true) || this.canMoveVertically(false) ||
                this.canMoveHorizontally(true) || this.canMoveHorizontally(false);
    }

    open fun move(field: Field = this.board.getBlackField()){
        val tempX = this.x;
        val tempY = this.y;
        this.x = field.x;
        this.y = field.y;
        field.x = tempX;
        field.y = tempY;

        this.board.fieldsMatrix[this.y][this.x] = this;
        field.board.fieldsMatrix[field.y][field.x] = field;
    }

    fun isOnSameLine(field : Field) : Boolean {
        return this.x == field.x || this.y == field.y;
    }

    fun isNextToField(field: Field) : Boolean {
        val xDiff = abs(this.x - field.x);
        val yDiff = abs(this.y - field.y);
        return (xDiff == 1 && yDiff == 0) || (xDiff == 0 && yDiff == 1);
    }

    fun hasSamePositionAsOtherField() : Boolean {
        for(field in this.board.fields){
            if(field.x == this.x && field.y == this.y){
                return true;
            }
        }

        return false;
    }
}

