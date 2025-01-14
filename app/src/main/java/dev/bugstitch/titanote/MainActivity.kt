package dev.bugstitch.titanote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.bugstitch.titanote.ui.Titanote
import dev.bugstitch.titanote.ui.theme.TitanoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TitanoteTheme {
                Titanote()
            }
        }
    }
}
