package dev.ranga.hpcharacters.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.ranga.hpcharacters.ui.navigation.AppNavHost
import dev.ranga.hpcharacters.ui.theme.HPCharactersTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            HPCharactersTheme {
                AppNavHost(navController)
                //MyScreen()
            }
        }
    }
}
@Composable
fun MyScreen() {
    Text("Hello, world!") // This will show something
}