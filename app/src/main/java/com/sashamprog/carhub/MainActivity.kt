package com.sashamprog.carhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sashamprog.carhub.ui.features.auth.AuthScreen
import com.sashamprog.carhub.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appNavController = rememberNavController()
            AppTheme {
                AppNavigation(appNavController)
            }
        }
    }
}

@Composable
fun AppNavigation(appNavController: NavHostController) {
    NavHost(appNavController, startDestination = "main") {
        composable("auth") { AuthScreen(appNavController) }
        composable("main") { MainScreen(appNavController) }
    }
}
