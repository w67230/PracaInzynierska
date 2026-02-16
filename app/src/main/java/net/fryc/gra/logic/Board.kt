package net.fryc.gra.logic

import androidx.compose.ui.graphics.Color
import net.fryc.gra.MainActivity
import net.fryc.gra.storage.settings.Settings
import net.fryc.gra.ui.screen.redraw
import java.sql.Time
import java.time.Clock
import java.time.Duration
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class Board(
    val size : Int,
    val difficulty: Difficulty,
    val settings: Settings,
    val showTimer : Boolean = true,
    val showMoves : Boolean = true
) {

    val fields = ArrayList<Field>()
    val fieldsMatrix : ArrayList<ArrayList<Field>>

    val timer : Clock = Clock.tick(Clock.systemDefaultZone(), Duration.ofSeconds(1))
    val startTime : Long = timer.millis()

    var moves : Long = 0
    var redFieldsCount = 0
    var greenFieldsCount = 0
    var blueFieldsCount = 0
    var magentaFieldsCount = 0
    var grayFieldsCount = 0
    var yellowFieldsCount = 0
    var sameValuesAmount = 0

    var uiUpdateFun : (MainActivity, Board) -> Unit = { activity, board ->
        redraw(board, activity)
    }

    init{
        this.createFields()
        this.fieldsMatrix = createFieldsMatrix()
    }

    private fun createFields(){
        this.fields.add(createBlackField())

        var i = this.fields.size
        val numberOfFields = this.size*this.size
        while(i < numberOfFields){
            this.fields.add(this.createRandomNonConflictingField())
            i++
        }

        while(this.difficulty != Difficulty.EASY && this.difficulty.getSameNumbersAmount() > this.sameValuesAmount){
            val field = this.getRandomNonBlackField()
            val possibleFields = this.fields.filter {
                return@filter it.value != field.value && it.color == field.color
            }.toList()

            var field2 : Field? = null
            for(x in possibleFields){
                field2 = possibleFields.filter { x.value != it.value }.getOrNull(0)
                if(field2 != null) break
            }

            if(field2 != null){
                this.fields[this.fields.indexOf(field2)] = Field(field2.y, field2.x, field2.color, field.value, field2.board)
                this.sameValuesAmount++
            }
        }
    }

    fun createFieldsMatrix() : ArrayList<ArrayList<Field>> {
        val matrix : ArrayList<ArrayList<Field>> = ArrayList()
        var y = 0
        while(y < this.size){
            val row = ArrayList<Field>()
            var x = 0
            while(x < this.size){
                this.getFieldFromFields(x, y)?.let { row.add(it) }

                x++
            }

            matrix.add(row)
            y++
        }

        return matrix
    }

    private fun getRandomNonBlackField() : Field {
        var field : Field
        do{
            field = this.fields[Random.nextInt(0, this.fields.size)]
        } while(field is BlackField)

        return field
    }

    private fun createBlackField() : Field {
        return BlackField(Random.nextInt(0, this.size), Random.nextInt(0, this.size), -1, this)
    }

    private fun createRandomNonConflictingField() : Field {
        val color = this.getRandomNonConflictingColor()
        val field = Field(
            Random.nextInt(0, this.size),
            Random.nextInt(0, this.size),
            color,
            this.getRandomNonConflictingValue(color),
            this
        )

        while(field.hasSamePositionAsOtherField()){
            field.x = Random.nextInt(0, this.size)
            field.y = Random.nextInt(0, this.size)
        }

        return field
    }

    private fun getRandomNonConflictingColor() : Color {
        var number : Int
        do{
            number = Random.nextInt(0,this.size)
        }while(!this.isColorAvailable(number))

        when(number){
            0 -> {
                redFieldsCount++
                return Color(this.settings.firstRed, this.settings.firstGreen, this.settings.firstBlue)
            }

            1 -> {
                greenFieldsCount++
                return Color(this.settings.secondRed, this.settings.secondGreen, this.settings.secondBlue)
            }

            2 -> {
                blueFieldsCount++
                return Color(this.settings.thirdRed, this.settings.thirdGreen, this.settings.thirdBlue)
            }

            3 -> {
                magentaFieldsCount++
                return Color(this.settings.fourthRed, this.settings.fourthGreen, this.settings.fourthBlue)
            }

            4 -> {
                grayFieldsCount++
                return Color(this.settings.fifthRed, this.settings.fifthGreen, this.settings.fifthBlue)
            }

            else -> {
                yellowFieldsCount++
                return Color(this.settings.sixthRed, this.settings.sixthGreen, this.settings.sixthBlue)
            }
        }
    }

    private fun isColorAvailable(number : Int) : Boolean {
        return when(number){
            0 -> redFieldsCount < this.size
            1 -> greenFieldsCount < this.size
            2 -> blueFieldsCount < this.size
            3 -> magentaFieldsCount < this.size
            4 -> grayFieldsCount < this.size
            else -> yellowFieldsCount < this.size
        }
    }

    private fun getRandomNonConflictingValue(color: Color) : Int {
        if(this.difficulty == Difficulty.EASY) return 0

        var value : Int

        do {
            value = Random.nextInt(1, 100)
        } while(!isValueAvailable(value, color))

        return value
    }

    private fun isValueAvailable(value : Int, color : Color) : Boolean {
        if(this.difficulty.getSameNumbersAmount() < 0) return true

        val available = this.fields.filter {
            return@filter it.value == value && it.color == color
        }.isEmpty()

        if(!available && this.difficulty.getSameNumbersAmount() > this.sameValuesAmount){
            this.sameValuesAmount++
            return true
        }

        return available
    }

    fun getBlackField() : Field {
        if(this.fields.isEmpty()){
            this.createFields()
        }
        for (field in this.fields){
            if(field is BlackField) return field
        }

        throw Exception("Black field doesn't exist! It should never happen!")
    }

    fun getFieldFromFields(x : Int, y : Int) : Field? {
        for(field in this.fields){
            if(field.x == x && field.y == y){
                return field
            }
        }

        return null
    }

    fun getFieldFromMatrix(x : Int, y : Int) : Field? {
        if(x >= this.size || y >= this.size) return null
        if(x < 0 || y < 0) return null

        return this.fieldsMatrix[y][x]
    }

    fun checkWin(): Boolean {
        return this.checkRowWin() || this.checkColumnWin()
    }

    fun checkRowWin() : Boolean {
        return this.checkFieldCorrectness(true)
    }

    fun checkColumnWin() : Boolean {
        return this.checkFieldCorrectness(false)
    }

    fun checkFieldCorrectness(forRows : Boolean) : Boolean {
        var y = 0
        var x = 0
        while ((if(forRows) y else x) < this.size) {
            var previousColor = Color.Unspecified
            var previousValue = if(this.difficulty != Difficulty.EASY) 0 else -1
            while ((if(forRows) x else y) < this.size) {
                val field = this.getFieldFromMatrix(x, y)
                if (field != null) {
                    if(this.compareColors(previousColor, field.color)){
                        previousColor = if(field.color == Color.Unspecified) previousColor else field.color

                        if(this.compareValues(previousValue, field.value)){
                            previousValue = if(field.value == -1) previousValue else field.value
                        }
                        else {

                            return false
                        }
                    }
                    else {

                        return false
                    }
                }
                else {
                    throw NullPointerException("Couldn't find field in matrix: x=$x , y=$y")
                }

                if(forRows) x++ else y++
            }

            if(forRows) {
                x = 0
                y++
            }
            else {
                y = 0
                x++
            }
        }

        return true
    }

    /**
     * Returns true when colors are the same or one of the colors is unspecified
     */
    fun compareColors(color1 : Color, color2 : Color) : Boolean {
        return if(color1 == Color.Unspecified || color2 == Color.Unspecified) true else color1 == color2
    }

    /**
     * Returns true when first value is lower than second one, or when second value is 0 (or lower)
     */
    fun compareValues(value1 : Int, value2 : Int) : Boolean {
        return if(value2 < 1) true else value1 <= value2
    }

    fun increaseMovesCount() {
        this.moves++
    }

    fun getCurrentTime() : kotlin.time.Duration {
        return Time.from(this.timer.instant()).time.minus(this.startTime).milliseconds
    }

    fun forceUiUpdate(activity : MainActivity, board : Board = this) {
        this.uiUpdateFun.invoke(activity, board)
    }

}