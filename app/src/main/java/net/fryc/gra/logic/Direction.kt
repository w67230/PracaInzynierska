package net.fryc.gra.logic

enum class Direction {

    UP {
        override fun isDirectionalMovingPossible(field : Field, blackField : Field): Boolean {
            return field.canMoveVertically(true, blackField)
        }

        override fun getNextField(field: Field): Field? {
            return field.board.getFieldFromMatrix(field.x, field.y - 1)
        }
    },
    DOWN {
        override fun isDirectionalMovingPossible(field : Field, blackField : Field): Boolean {
            return field.canMoveVertically(false, blackField)
        }

        override fun getNextField(field: Field): Field? {
            return field.board.getFieldFromMatrix(field.x, field.y + 1)
        }
    },
    LEFT {
        override fun isDirectionalMovingPossible(field : Field, blackField : Field): Boolean {
            return field.canMoveHorizontally(true, blackField)
        }

        override fun getNextField(field: Field): Field? {
            return field.board.getFieldFromMatrix(field.x - 1, field.y)
        }
    },
    RIGHT {
        override fun isDirectionalMovingPossible(field : Field, blackField : Field): Boolean {
            return field.canMoveHorizontally(false, blackField)
        }

        override fun getNextField(field: Field): Field? {
            return field.board.getFieldFromMatrix(field.x + 1, field.y)
        }
    },
    UNSPECIFIED {
        override fun isDirectionalMovingPossible(field : Field, blackField : Field): Boolean {
            return false
        }

        override fun getNextField(field: Field): Field? {
            return field
        }
    };

    abstract fun isDirectionalMovingPossible(field : Field, blackField : Field = field.board.getBlackField()) : Boolean

    abstract fun getNextField(field : Field) : Field?
}