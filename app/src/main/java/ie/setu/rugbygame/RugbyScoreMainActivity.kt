package ie.setu.rugbygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.rugbygame.ui.screens.home.HomeScreen
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme

@AndroidEntryPoint
class RugbyScoreMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RugbyScoreTheme { HomeScreen() }
        }
    }
}
