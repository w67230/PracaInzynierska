package net.fryc.gra.ui.screen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em


@Composable
fun showSimpleText(resourceString : Int){
    Text(text = stringResource(resourceString), modifier = Modifier.padding(bottom = 10.dp));
}

@Composable
fun addNavigationBar(modifier : Modifier, onBackPress : () -> Unit, showHelp : Boolean, onHelpPress : () -> Unit) {
    NavigationBar(modifier = modifier.height(40.dp)) {
        IconButton({
            onBackPress.invoke();
        }) {
            Text("<-", fontWeight = FontWeight.Bold, fontSize = 5.em);
        }

        if(showHelp){
            IconButton({
                onHelpPress.invoke();
            }) {
                Text("?", fontWeight = FontWeight.Bold, fontSize = 5.em);
            }
        }
    }
}