// TradeSdk.kt
package com.finoux.tradesdk

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.runtime.mutableStateOf

object TradeSdk {

    private var _navController: NavHostController? = null
    private val navController get() = _navController!!

    private val _shouldShowHost = mutableStateOf(false)
    private val _startIsin = mutableStateOf<String?>(null)

    // Call this to trigger SDK flow from outside
    fun openStockDetails(isin: String) {
        _startIsin.value = isin
        _shouldShowHost.value = true
    }

    // Host composable to be placed once in parent app
    @Composable
    fun Host() {
        if (_shouldShowHost.value) {
            val navController = rememberNavController()
            _navController = navController

            NavHost(navController, startDestination = "stock_details") {
                composable("stock_details") {
                    val isin = _startIsin.value
                    if (isin != null) {
                        StockDetailScreen(isin)
                    }
                }
            }
        }
    }
}
