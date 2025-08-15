package com.godilabs.depensy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.godilabs.depensy.data.database.ExpenseDatabase
import com.godilabs.depensy.data.dao.ExpenseDao
import com.godilabs.depensy.data.dao.BudgetDao
import com.godilabs.depensy.data.repository.BudgetRepository
import com.godilabs.depensy.data.repository.ExpenseRepository
import com.godilabs.depensy.ui.theme.DepensyTheme
import com.godilabs.depensy.viewmodel.BudgetViewModel
import com.godilabs.depensy.viewmodel.ExpenseViewModel
import com.godilabs.depensy.ui.screens.DashboardScreen
import com.godilabs.depensy.ui.screens.AddExpenseScreen
import com.godilabs.depensy.ui.screens.HistoryScreen
import com.godilabs.depensy.ui.screens.BudgetScreen
import com.godilabs.depensy.ui.screens.SettingsScreen
import com.godilabs.depensy.ui.screens.ScanScreen

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Accueil", Icons.Filled.Home)
    object Scan : Screen("scan", "Scan", Icons.Filled.QrCodeScanner)
    object AddExpense : Screen("add_expense", "Ajouter", Icons.Filled.Add)
    object History : Screen("history", "Historique", Icons.Filled.History)
    object Budget : Screen("budget", "Budget", Icons.Filled.AccountBalanceWallet)
    object Settings : Screen("settings", "Paramètres", Icons.Filled.Settings)
    companion object {
        val bottomScreens = listOf(Dashboard, History, Scan, Budget, Settings)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // White status bar with dark icons
        window.statusBarColor = android.graphics.Color.WHITE
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
        setContent {
            DepensyTheme {
                val navController = rememberNavController()
                // Set up dependencies with Room database
                val context = LocalContext.current
                val database = remember { ExpenseDatabase.getDatabase(context) }

                val expenseDao: ExpenseDao = remember { database.expenseDao() }
                val expenseRepository = remember { ExpenseRepository(expenseDao) }
                val expenseViewModel = remember { ExpenseViewModel(expenseRepository) }

                val budgetDao: BudgetDao = remember { database.budgetDao() }
                val budgetRepository = remember { BudgetRepository(budgetDao) }
                val budgetViewModel = remember { BudgetViewModel(budgetRepository) }

                DepensyNavScaffold(
                    navController = navController,
                    expenseViewModel = expenseViewModel,
                    budgetViewModel = budgetViewModel
                )
            }
        }
    }
}

@Composable
fun DepensyNavScaffold(
    navController: NavHostController,
    expenseViewModel: ExpenseViewModel,
    budgetViewModel: BudgetViewModel
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val showBottomBar = currentRoute != Screen.AddExpense.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = androidx.compose.ui.graphics.Color.White,
                    contentColor = androidx.compose.ui.graphics.Color.Black
                ) {
                    Screen.bottomScreens.forEach { screen ->
                        val selected = currentRoute == screen.route
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    screen.icon,
                                    contentDescription = screen.label,
                                    tint = androidx.compose.ui.graphics.Color.Black
                                )
                            },
                            label = {
                                androidx.compose.material3.Text(
                                    screen.label,
                                    color = androidx.compose.ui.graphics.Color.Black
                                )
                            },
                            selected = navBackStackEntry.value?.destination?.hierarchy?.any { it.route == screen.route } == true,
                            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                                selectedIconColor = androidx.compose.ui.graphics.Color.Black,
                                unselectedIconColor = androidx.compose.ui.graphics.Color.Black.copy(
                                    alpha = 0.6f
                                ),
                                selectedTextColor = androidx.compose.ui.graphics.Color.Black,
                                unselectedTextColor = androidx.compose.ui.graphics.Color.Black.copy(
                                    alpha = 0.6f
                                ),
                                indicatorColor = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.1f)
                            ),
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    viewModel = expenseViewModel,
                    onAddClick = { navController.navigate(Screen.AddExpense.route) },
                    onViewMoreClick = { navController.navigate(Screen.History.route) },
                    modifier = Modifier.padding(if (showBottomBar) innerPadding else PaddingValues(0.dp))
                )
            }
            composable(Screen.Scan.route) {
                ScanScreen(
                    onBack = { navController.navigateUp() },
                    modifier = Modifier.padding(
                        if (showBottomBar) innerPadding else PaddingValues(
                            0.dp
                        )
                    )
                )
            }
            composable(Screen.AddExpense.route) {
                AddExpenseScreen(
                    viewModel = expenseViewModel,
                    onBack = { navController.navigateUp() },
                    modifier = Modifier.padding(if (showBottomBar) innerPadding else PaddingValues(0.dp))
                )
            }
            // Add stubs for other screens:
            composable(Screen.History.route) {
                HistoryScreen(
                    viewModel = expenseViewModel,
                    modifier = Modifier.padding(
                        if (showBottomBar) innerPadding else PaddingValues(0.dp)
                    )
                )
            }
            composable(Screen.Budget.route) {
                BudgetScreen(
                    viewModel = budgetViewModel,
                    expenseViewModel = expenseViewModel,
                    modifier = Modifier.padding(
                        if (showBottomBar) innerPadding else PaddingValues(
                            0.dp
                        )
                    )
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    modifier = Modifier.padding(
                        if (showBottomBar) innerPadding else PaddingValues(
                            0.dp
                        )
                    )
                )
            }
        }
    }
}