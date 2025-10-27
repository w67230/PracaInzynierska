package net.fryc.gra

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.fryc.gra.storage.AppDataContainer
import net.fryc.gra.storage.AppViewModel
import net.fryc.gra.storage.score.Score
import net.fryc.gra.storage.settings.Settings
import net.fryc.gra.ui.screen.startMenu
import java.util.Stack
import java.util.logging.Logger

class MainActivity() : ComponentActivity() {

    var container : AppDataContainer? = null;
    var viewModel : ViewModel? = null;
    var settings : Settings = Settings(
        0,
        false, false, false, false,
        Color.Red.red, Color.Red.green, Color.Red.blue,
        Color.Green.red, Color.Green.green, Color.Green.blue,
        Color.Blue.red, Color.Blue.green, Color.Blue.blue,
        Color.Magenta.red, Color.Magenta.green, Color.Magenta.blue,
        Color.DarkGray.red, Color.DarkGray.green, Color.DarkGray.blue,
        Color.Yellow.red, Color.Yellow.green, Color.Yellow.blue
    );
    var scores : List<Score> = ArrayList<Score>();
    val backStack : Stack<(MainActivity) -> Unit> = Stack();

    companion object {
        val LOGGER : Logger = Logger.getLogger("gra");
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        //startGame(4,Difficulty.EASY, this);
        // TODO dac obsluge poziomej orientacji
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        this.viewModel = AppViewModel();
        this.container = AppDataContainer(this.baseContext);
        this.updateSettings();
        this.updateScores();

        startMenu(this);
    }


    override fun onBackPressed() {
        if(this.backStack.isEmpty()){
            super.onBackPressed();
        }
        else {
            this.backStack.pop().invoke(this);
        }
    }

    fun updateSettings() {
        this.viewModel?.viewModelScope?.launch {
            this@MainActivity.container?.settingsRepository?.getOptions()?.collect {
                if (it != null) {
                    this@MainActivity.settings = it;
                }
            }
        }
    }

    fun updateScores() {
        this.viewModel?.viewModelScope?.launch {
            this@MainActivity.container?.scoreRepository?.getAllScores()?.collect {
                this@MainActivity.scores = it;
            }
        }
    }
}
