package net.fryc.gra.board

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

class Board(val size : Int, val difficulty: Difficulty) {

    val fields = ArrayList<Field>();
    var redFieldsCount = 0;
    var greenFieldsCount = 0;
    var blueFieldsCount = 0;
    var magentaFieldsCount = 0;
    var grayFieldsCount = 0;
    var yellowFieldsCount = 0;

    init{
        this.createFields();
    }

    private fun createFields(){
        this.fields.add(createBlackField());

        var i = this.fields.size;
        val numberOfFields = this.size*this.size;
        while(i < numberOfFields){
            this.fields.add(this.createRandomNonConflictingField());
            i++;
        }
    }

    private fun createBlackField() : Field {
        return Field(0,0, Color.Transparent,-1, this);
    }

    private fun createRandomNonConflictingField() : Field {
        val field = Field(Random.nextInt(0, this.size), Random.nextInt(0, this.size), this.getRandomNonConflictingColor(), Random.nextInt(1, 100), this);

        while(field.hasSamePositionAsOtherField()){
            field.x = Random.nextInt(0, this.size);
            field.y = Random.nextInt(0, this.size);
        }

        return field;
    }

    private fun getRandomNonConflictingColor() : Color {
        var number : Int;
        do{
            number = Random.nextInt(0,this.size);
        }while(!this.isColorAvailable(number));

        when(number){
            0 -> {
                redFieldsCount++;
                return Color.Red
            };
            1 -> {
                greenFieldsCount++;
                return Color.Green
            };
            2 -> {
                blueFieldsCount++;
                return Color.Cyan
            };
            3 -> {
                magentaFieldsCount++;
                return Color.Magenta
            };
            4 -> {
                grayFieldsCount++;
                return Color.DarkGray
            };
            else -> {
                yellowFieldsCount++;
                return Color.Yellow
            };
        }
    }

    private fun isColorAvailable(number : Int) : Boolean {
        return when(number){
            0 -> redFieldsCount < this.size;
            1 -> greenFieldsCount < this.size;
            2 -> blueFieldsCount < this.size;
            3 -> if(this.size > 4) magentaFieldsCount < this.size else magentaFieldsCount < this.size-1;
            4 -> if(this.size > 5) grayFieldsCount < this.size else grayFieldsCount < this.size-1;
            else -> yellowFieldsCount < this.size-1;
        }
    }

    fun getBlackField() : Field {
        if(this.fields.isEmpty()){
            this.createFields();
        }
        for (field in this.fields){
            if(field.value < 0) return field;
        }

        throw Exception("Black field doesn't exist! It should never happen!");
    }

    fun getField(x : Int, y : Int) : Field? {
        for(field in this.fields){
            if(field.x == x && field.y == y){
                return field;
            }
        }

        return null;
    }

    fun checkWin(): Boolean {
        return this.checkRowWin() || this.checkColumnWin();
    }

    fun checkRowWin() : Boolean {
        return this.checkFieldCorrectness(true);
    }

    fun checkColumnWin() : Boolean {
        return this.checkFieldCorrectness(false);
    }

    fun checkFieldCorrectness(forRows : Boolean) : Boolean {
        var y = 0;
        var x = 0;
        while ((if(forRows) y else x) < this.size) {
            var previousColor = Color.Unspecified;
            var previousValue = if(this.difficulty != Difficulty.EASY) 0 else -1;
            while ((if(forRows) x else y) < this.size) {
                val field = this.getField(x, y);
                if (field != null) {
                    if(this.compareColors(previousColor, field.color)){
                        previousColor = if(field.color == Color.Unspecified) previousColor else field.color;

                        if(this.compareValues(previousValue, field.value)){
                            previousValue = if(field.value == -1) previousValue else field.value;
                        }
                        else {
                            return false;
                        }
                    }
                    else {
                        return false;
                    }
                }
                // TODO skoro ide po szerokosci  i wysokosci planszy, to pole nigdy nie powinno byc null, ale moge dac tu jakis wyjatek jakby jakims cudem sie tak stalo

                if(forRows) x++ else y++;
            }

            if(forRows) {
                x = 0;
                y++;
            }
            else {
                y = 0;
                x++;
            };
        }

        return true;
    }

    /**
     * Returns true when colors are the same or one of the colors is unspecified
     */
    fun compareColors(color1 : Color, color2 : Color) : Boolean {
        return if(color1 == Color.Unspecified || color2 == Color.Unspecified) true else color1 == color2;
    }

    /**
     * Returns true when first value is lower than second one, or when second value is -1
     */
    fun compareValues(value1 : Int, value2 : Int) : Boolean {
        return if(value2 == -1) true else value1 < value2;
    }

}