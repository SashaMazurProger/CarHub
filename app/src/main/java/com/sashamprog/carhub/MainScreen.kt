package com.sashamprog.carhub

import CreateEditCarScreen
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sashamprog.carhub.ui.features.account.AccountScreen
import com.sashamprog.carhub.ui.features.account.AccountViewModel
import com.sashamprog.carhub.ui.features.catalog.CatalogNavGraph


@Composable
fun MainScreen() {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(bottomNavController) }
    ) { innerPadding ->
        BottomNavigationGraph(bottomNavController, Modifier.padding(innerPadding))
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(BottomNavItem.Catalog, BottomNavItem.AddCar, BottomNavItem.Account)
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Catalog :
        BottomNavItem("catalog", "Catalog", Icons.AutoMirrored.Filled.List)

    object AddCar :
        BottomNavItem("add_car", "Add Car", Icons.Default.AddCircle)

    object Account : BottomNavItem("account", "Account", Icons.Default.Person)
}

@Composable
fun BottomNavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, startDestination = BottomNavItem.Catalog.route, modifier) {
        composable(BottomNavItem.Catalog.route) { CatalogNavGraph() }
        composable(BottomNavItem.AddCar.route) { CreateEditCarScreen(navController) }
        composable(BottomNavItem.Account.route) { AccountScreen(navController) }
    }
}

