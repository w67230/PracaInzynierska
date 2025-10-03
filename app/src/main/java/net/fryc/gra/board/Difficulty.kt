package net.fryc.gra.board

enum class Difficulty {

    EASY {
        override fun getSameNumbersAmount(): Int {
            return -1;
        }
    },
    NORMAL {
        override fun getSameNumbersAmount(): Int {
            return 5;
        }
    },
    HARD {
        override fun getSameNumbersAmount(): Int {
            return 3;
        }
    },
    VERY_HARD {
        override fun getSameNumbersAmount(): Int {
            return 1;
        }
    };

    abstract fun getSameNumbersAmount() : Int;
}