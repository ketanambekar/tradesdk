package com.finoux.tradesdk

import OrderScreen
import OrderSuccessScreen
import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

object TradeSdk {

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

            // Initialize NavController locally inside the Host composable
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "stock_details") {
                composable("stock_details") {
                    val isin = _startIsin.value
                    if (isin != null) {
                        Log.d("TradeSdk", "Displaying StockDetailScreen for ISIN: $isin") // Log when stock details screen is being displayed
                        StockDetailScreen(
                            isin = isin,
                            onBack = {
                                Log.d("TradeSdk", "Back button pressed. Closing stock details.") // Log when back button is pressed
                                close() // Call close to hide the SDK
                                navController.popBackStack() // Ensure you pop the screen off the stack
                            }
                        )
                    }
                }
                composable("order_screen") {
                    OrderScreen(navController = navController) // Pass NavController for navigation
                }
                composable("order_confirmation") {
                    OrderSuccessScreen() // Screen for order confirmation
                }
            }
        } else {
            Log.d("TradeSdk", "Host composable is not shown.") // Log if Host composable isn't shown
        }
    }

    // State variables to track navigation and initialization
    private val _shouldShowHost = mutableStateOf(false)
    private val _startIsin = mutableStateOf<String?>(null)
}
