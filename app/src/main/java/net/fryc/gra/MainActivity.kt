package net.fryc.gra

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.fryc.gra.storage.AppDataContainer
import net.fryc.gra.storage.Settings
import net.fryc.gra.ui.screen.startMenu
import java.util.logging.Logger

class MainActivity(var isInMenu : Boolean = true) : ComponentActivity() {

    var container : AppDataContainer? = null;
    var settings : Settings = Settings(0, false, false);

    companion object {
        val LOGGER : Logger = Logger.getLogger("gra");
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        //startGame(4,Difficulty.EASY, this);
        // TODO dac obsluge poziomej orientacji
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        this.container = AppDataContainer(this.baseContext);
        this.updateSettings();

        startMenu(this);
    }

    // TODO lepsza obsluga przycisku cofania
    override fun onBackPressed() {
        if(this.isInMenu){
            super.onBackPressed();
        }
        else {
            startMenu(this);
        }
    }

    fun updateSettings() {
        this.container?.viewModelScope?.launch {
            this@MainActivity.container?.settingsRepository?.getOptions()?.collect {
                if (it != null) {
                    this@MainActivity.settings = it;
                }
            }
        }
    }
}
