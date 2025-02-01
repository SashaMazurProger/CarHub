package com.sashamprog.carhub

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sashamprog.carhub.ui.features.auth.AuthScreen
import com.sashamprog.carhub.ui.features.auth.AuthViewModel
import com.sashamprog.carhub.ui.features.register.RegisterScreen
import com.sashamprog.carhub.ui.theme.AppTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appNavController = rememberNavController()
            AppTheme {
                AppNavGraph(appNavController)
            }
        }
    }

    @Composable
    fun AppNavGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "login") {
            composable("register") {
                RegisterScreen(
                    navController = navController,
                    onSuccessAuth = ::onSuccessAuth
                )
            }
            composable("login") { AuthScreen(navController, ::onSuccessAuth) }
        }
    }

    private fun onSuccessAuth() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

