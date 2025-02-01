package com.sashamprog.carhub

import CreateEditCarScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sashamprog.carhub.ui.features.account.AccountScreen
import com.sashamprog.carhub.ui.features.catalog.CatalogNavGraph


@Composable
fun MainScreen(appNavController: NavHostController, onSignedOut: () -> Unit) {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(bottomNavController) }
    ) { innerPadding ->
        BottomNavigationGraph(
            bottomController = bottomNavController,
            appController = appNavController,
            modifier = Modifier.padding(innerPadding),
            onSignedOut = onSignedOut
        )
    }
}

@Composable
fun BottomNavigationBar(bottomNavController: NavController) {
    val items = listOf(BottomNavItem.Catalog, BottomNavItem.AddCar, BottomNavItem.Account)

    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = isSelected,
                onClick = {
                    bottomNavController.navigate(item.route) {
                        popUpTo(bottomNavController.graph.startDestinationId) { saveState = true }
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
fun BottomNavigationGraph(
    bottomController: NavHostController,
    appController: NavHostController,
    modifier: Modifier,
    onSignedOut: () -> Unit
) {
    NavHost(bottomController, startDestination = BottomNavItem.Catalog.route, modifier) {
        composable(BottomNavItem.Catalog.route) { CatalogNavGraph() }
        composable(BottomNavItem.AddCar.route) { CreateEditCarScreen(bottomController) }
        composable(BottomNavItem.Account.route) {
            AccountScreen(appController, onSignedOut)
        }
    }
}

