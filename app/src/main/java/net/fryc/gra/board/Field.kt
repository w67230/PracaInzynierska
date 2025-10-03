package net.fryc.gra.board

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
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
import net.fryc.gra.MainActivity
import net.fryc.gra.ui.redraw
import kotlin.math.abs


class Field(var y: Int, var x : Int, val color : Color, val value : Int, val board: Board) {

    @Composable
    fun drawBox(activity: MainActivity){
        Box(Modifier.height((90-this.board.size*5).dp).width((90-this.board.size*5).dp).padding(5.dp, 5.dp).background(this.color).clickable {
            if(this.canMove()){
                this.onClickOrDrag(activity);
            }
        }.draggable(DraggableState {
            if(this.canMoveHorizontally(it)){
                this.onClickOrDrag(activity);
            }
        }, Orientation.Horizontal).draggable(DraggableState {
            if(this.canMoveVertically(it)){
                this.onClickOrDrag(activity);
            }
        }, Orientation.Vertical)) {
            if(this@Field.value > 0 && this@Field.board.difficulty > Difficulty.EASY){
                Text(text = this@Field.value.toString(), modifier = Modifier.align(Alignment.Center), fontWeight = FontWeight.Bold, color = Color.Black);
            }
        }
    }

    fun canMoveHorizontally(dragFloat: Float, field: Field = this.board.getBlackField()) : Boolean {
        if(this.isNextToField(field)){
            return (field.x-this.x > 0 && dragFloat > 0) || (field.x-this.x < 0 && dragFloat < 0);
        }
        return false;
    }

    fun canMoveVertically(dragFloat: Float, field: Field = this.board.getBlackField()) : Boolean {
        if(this.isNextToField(field)){
            return (field.y-this.y < 0 && dragFloat < 0) || (field.y-this.y > 0 && dragFloat > 0);
        }
        return false;
    }
    fun canMove(field: Field = this.board.getBlackField()) : Boolean {
        return this.isNextToField(field);
    }

    fun onClickOrDrag(activity: MainActivity, field: Field = this.board.getBlackField()){
        var tempX = this.x;
        var tempY = this.y;
        this.x = field.x;
        this.y = field.y;
        field.x = tempX;
        field.y = tempY;

        redraw(this.board, activity);
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

