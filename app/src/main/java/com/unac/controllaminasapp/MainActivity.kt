package com.unac.controllaminasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.unac.controllaminasapp.ui.screens.BuscadorScreen
import com.unac.controllaminasapp.ui.screens.ObtenidasScreen
import com.unac.controllaminasapp.ui.screens.PendientesScreen
import com.unac.controllaminasapp.ui.screens.RepetidasScreen
import com.unac.controllaminasapp.ui.theme.ControlLaminasAppTheme
import com.unac.controllaminasapp.viewmodel.LaminaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ControlLaminasAppTheme {
                val viewModel: LaminaViewModel = viewModel()
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { BottomNavBar(navController) }
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Obtenidas.route,
                        modifier = Modifier.fillMaxSize().padding(padding)
                    ) {
                        composable(Screen.Obtenidas.route) { ObtenidasScreen(viewModel) }
                        composable(Screen.Pendientes.route) { PendientesScreen(viewModel) }
                        composable(Screen.Repetidas.route) { RepetidasScreen(viewModel) }
                        composable(Screen.Buscador.route) { BuscadorScreen(viewModel) }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Obtenidas : Screen("obtenidas", "Obtenidas", Icons.Default.Done)
    data object Pendientes : Screen("pendientes", "Pendientes", Icons.AutoMirrored.Filled.List)
    data object Repetidas : Screen("repetidas", "Repetidas", Icons.Default.Refresh)
    data object Buscador : Screen("buscador", "Buscar", Icons.Default.Search)
}

@Composable
fun BottomNavBar(navController: androidx.navigation.NavController) {
    val items = listOf(Screen.Obtenidas, Screen.Pendientes, Screen.Repetidas, Screen.Buscador)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
