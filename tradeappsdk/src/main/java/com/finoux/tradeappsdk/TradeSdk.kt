package com.finoux.tradesdk

import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

object TradeSdk {

    private var _navController: NavHostController? = null
    private val navController get() = _navController!!
    private val _shouldShowHost = mutableStateOf(false)
    private val _startIsin = mutableStateOf<String?>(null)

    private var onCloseCallback: (() -> Unit)? = null

    fun openStockDetails(isin: String) {
        Log.d("TradeSdk", "openStockDetails called with ISIN: $isin") // Log when opening stock details
        _startIsin.value = isin
        _shouldShowHost.value = true
    }

    fun close() {
        Log.d("TradeSdk", "close called, hiding stock details") // Log when closing the SDK
        _shouldShowHost.value = false
        onCloseCallback?.invoke()
    }

    @Composable
    fun Host(onClose: () -> Unit) {
        onCloseCallback = onClose

        if (_shouldShowHost.value) {
            Log.d("TradeSdk", "Host composable displayed. Navigating to stock details.") // Log when Host is shown

            val navController = rememberNavController()
            _navController = navController

            NavHost(navController, startDestination = "stock_details") {
                composable("stock_details") {
                    val isin = _startIsin.value
                    if (isin != null) {
                        Log.d("TradeSdk", "Displaying StockDetailScreen for ISIN: $isin") // Log when stock details screen is being displayed
                        StockDetailScreen(isin = isin, onBack = {
                            Log.d("TradeSdk", "Back button pressed. Closing stock details.") // Log when back button is pressed
                            close()
                        })
                    }
                }
            }
        } else {
            Log.d("TradeSdk", "Host composable is not shown.") // Log if Host composable isn't shown
        }
    }
}
