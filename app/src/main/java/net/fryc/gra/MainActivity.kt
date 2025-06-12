package net.fryc.gra

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import net.fryc.gra.ui.startMenu

class MainActivity(var isInMenu : Boolean = true) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        //startGame(4,Difficulty.EASY, this);
        // TODO dac obsluge poziomej orientacji
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        startMenu(this);
    }

    // TODO dac pasek nawigacyjny i lepsza obsluge przycisku cofania
    override fun onBackPressed() {
        if(this.isInMenu){
            super.onBackPressed();
        }
        else {
            startMenu(this);
        }
    }
}
