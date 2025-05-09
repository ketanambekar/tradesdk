package com.finoux.tradesdk

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object TradeSdk {

    // Shared state to trigger navigation
    private var pendingIsin: String? by mutableStateOf(null)

    /**
     * Called from the main app to request opening stock details.
     */
    fun openStockDetails(isin: String) {
        pendingIsin = isin
    }

    /**
     * The Composable host that handles the navigation for SDK.
     * Must be called from the main app inside a Composable.
     */
    @Composable
    fun SDKNavHost() {
        val navController = rememberNavController()

        // Handle navigation to stock details when openStockDetails is called
        LaunchedEffect(pendingIsin) {
            pendingIsin?.let {
                navController.navigate("stock_details/$it")
                pendingIsin = null // Reset after navigation
            }
        }

        NavHost(navController = navController, startDestination = "stock_list") {
            composable("stock_list") {
                StockListScreen()
            }
            composable("stock_details/{isin}") { backStackEntry ->
                val isin = backStackEntry.arguments?.getString("isin") ?: ""
                StockDetailScreen(isin)
            }
        }
    }
}
